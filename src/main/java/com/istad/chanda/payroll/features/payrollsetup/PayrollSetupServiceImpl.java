package com.istad.chanda.payroll.features.payrollsetup;

import com.istad.chanda.payroll.features.payrollsetup.dto.*;
import org.springframework.transaction.annotation.Transactional;
import com.istad.chanda.payroll.features.employee.EmployeeRepository;
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
public class PayrollSetupServiceImpl implements PayrollSetupService {
    private final PayrollSetupRepository repository;
    private final PayrollSetupMapper mapper;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public PayrollSetupResponse findById(String id) { return mapper.toResponse(find(id)); }

    @Override
    @Transactional(readOnly = true)
    public Page<PayrollSetupResponse> findAll(Pageable pageable) {
        if (pageable.isUnpaged() || pageable.getPageSize() > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageSize must be between 1 and 100");
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PayrollSetupResponse> search(String name) {
        String term = name == null ? "" : name.trim();
        return repository.findByEmployee_FirstNameContainingIgnoreCaseOrEmployee_LastNameContainingIgnoreCaseOrEmployee_EmployeeCodeContainingIgnoreCase(term, term, term).stream().map(mapper::toResponse).toList();
    }

    @Override
    public PayrollSetupResponse createNew(CreatePayrollSetupRequest request) {
        PayrollSetup entity = mapper.toEntity(request);
        beforeSave(entity);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public PayrollSetupResponse updateById(String id, UpdatePayrollSetupRequest request) {
        PayrollSetup entity = locked(id);
        mapper.update(request, entity);
        beforeSave(entity);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public void deleteById(String id) {
        PayrollSetup entity = locked(id);
        repository.delete(entity);
        repository.flush();
    }

    private PayrollSetup find(String id) { return repository.findById(id).orElseThrow(() -> missing(id)); }
    private PayrollSetup locked(String id) { return repository.findLockedById(id).orElseThrow(() -> missing(id)); }
    private ResponseStatusException missing(String id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found: " + id);
    }
    private void require(boolean condition, String message) {
        if (!condition) throw new ResponseStatusException(HttpStatus.CONFLICT, message);
    }
    private void beforeSave(PayrollSetup entity) {
        var employee = employeeRepository.findById(entity.getEmployeeId()).orElseThrow(() -> missing(entity.getEmployeeId()));
    }
}
