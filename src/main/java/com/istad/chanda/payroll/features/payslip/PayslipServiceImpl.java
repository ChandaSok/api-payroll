package com.istad.chanda.payroll.features.payslip;

import com.istad.chanda.payroll.features.payslip.dto.*;
import org.springframework.transaction.annotation.Transactional;
import com.istad.chanda.payroll.features.employee.EmployeeRepository;
import com.istad.chanda.payroll.features.payrollrun.PayrollRunRepository;
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
public class PayslipServiceImpl implements PayslipService {
    private final PayslipRepository repository;
    private final PayslipMapper mapper;
    private final EmployeeRepository employeeRepository;
    private final PayrollRunRepository payrollrunRepository;

    @Override
    @Transactional(readOnly = true)
    public PayslipResponse findById(String id) { return mapper.toResponse(find(id)); }

    @Override
    @Transactional(readOnly = true)
    public Page<PayslipResponse> findAll(Pageable pageable) {
        if (pageable.isUnpaged() || pageable.getPageSize() > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageSize must be between 1 and 100");
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PayslipResponse> search(String name) {
        String term = name == null ? "" : name.trim();
        return repository.findByEmployeeNameContainingIgnoreCaseOrEmployeeCodeContainingIgnoreCase(term, term).stream().map(mapper::toResponse).toList();
    }

    @Override
    public PayslipResponse createNew(CreatePayslipRequest request) {
        Payslip entity = mapper.toEntity(request);
        beforeSave(entity);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public PayslipResponse updateById(String id, UpdatePayslipRequest request) {
        Payslip entity = locked(id);
        require(entity.getPayrollRunId().equals(request.payrollRunId()), "A payslip cannot be moved to another run");
        beforeChange(entity);
        mapper.update(request, entity);
        beforeSave(entity);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public void deleteById(String id) {
        Payslip entity = locked(id);
        beforeChange(entity);
        repository.delete(entity);
        repository.flush();
    }

    private Payslip find(String id) { return repository.findById(id).orElseThrow(() -> missing(id)); }
    private Payslip locked(String id) { return repository.findLockedById(id).orElseThrow(() -> missing(id)); }
    private ResponseStatusException missing(String id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found: " + id);
    }
    private void require(boolean condition, String message) {
        if (!condition) throw new ResponseStatusException(HttpStatus.CONFLICT, message);
    }
    private void beforeSave(Payslip entity) {
        var employee = employeeRepository.findById(entity.getEmployeeId()).orElseThrow(() -> missing(entity.getEmployeeId()));
        var run = payrollrunRepository.findLockedById(entity.getPayrollRunId()).orElseThrow(() -> missing(entity.getPayrollRunId()));
        require(run.getStatus() == com.istad.chanda.payroll.features.payrollrun.PayrollRunStatus.DRAFT, "Payslips can only be changed in draft runs");
        entity.setEmployeeCode(employee.getEmployeeCode());
        entity.setEmployeeName(employee.getFirstName() + " " + employee.getLastName());
        require(entity.getNetPay().signum() >= 0, "Deductions cannot exceed gross pay");
    }
    private void beforeChange(Payslip entity) {
        var run = payrollrunRepository.findLockedById(entity.getPayrollRunId()).orElseThrow(() -> missing(entity.getPayrollRunId()));
        require(run.getStatus() == com.istad.chanda.payroll.features.payrollrun.PayrollRunStatus.DRAFT, "Finalized payslips cannot be changed");
    }
}
