package com.istad.chanda.payroll.features.payrollrun;

import com.istad.chanda.payroll.features.payrollrun.dto.*;
import org.springframework.transaction.annotation.Transactional;
import com.istad.chanda.payroll.features.payslip.PayslipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PayrollRunServiceImpl implements PayrollRunService {
    private final PayrollRunRepository repository;
    private final PayrollRunMapper mapper;
    private final PayslipRepository payslipRepository;

    @Override
    @Transactional(readOnly = true)
    public PayrollRunResponse findById(String id) { return mapper.toResponse(find(id)); }

    @Override
    @Transactional(readOnly = true)
    public Page<PayrollRunResponse> findAll(Pageable pageable) {
        if (pageable.isUnpaged() || pageable.getPageSize() > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageSize must be between 1 and 100");
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PayrollRunResponse> search(String name) {
        String term = name == null ? "" : name.trim();
        return repository.findByNameContainingIgnoreCase(term).stream().map(mapper::toResponse).toList();
    }

    @Override
    public PayrollRunResponse createNew(CreatePayrollRunRequest request) {
        PayrollRun entity = mapper.toEntity(request);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public PayrollRunResponse updateById(String id, UpdatePayrollRunRequest request) {
        PayrollRun entity = locked(id);
        beforeChange(entity);
        mapper.update(request, entity);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public void deleteById(String id) {
        PayrollRun entity = locked(id);
        beforeChange(entity);
        repository.delete(entity);
        repository.flush();
    }

    private PayrollRun find(String id) { return repository.findById(id).orElseThrow(() -> missing(id)); }
    private PayrollRun locked(String id) { return repository.findLockedById(id).orElseThrow(() -> missing(id)); }
    private ResponseStatusException missing(String id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found: " + id);
    }
    private void require(boolean condition, String message) {
        if (!condition) throw new ResponseStatusException(HttpStatus.CONFLICT, message);
    }
    private void beforeChange(PayrollRun entity) {
        require(entity.getStatus() == PayrollRunStatus.DRAFT, "Finalized payroll runs cannot be changed");
        require(!payslipRepository.existsByPayrollRunId(entity.getId()), "Remove draft payslips before changing or deleting this run");
    }
    @Override
    public PayrollRunResponse finalizeRun(String id) {
        PayrollRun run = locked(id);
        require(run.getStatus() == PayrollRunStatus.DRAFT, "Run is already finalized");
        require(payslipRepository.existsByPayrollRunId(id), "A run needs at least one payslip before finalization");
        run.setStatus(PayrollRunStatus.FINALIZED);
        return mapper.toResponse(repository.saveAndFlush(run));
    }
}
