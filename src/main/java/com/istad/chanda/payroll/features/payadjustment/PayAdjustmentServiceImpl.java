package com.istad.chanda.payroll.features.payadjustment;

import com.istad.chanda.payroll.features.payadjustment.dto.*;
import org.springframework.transaction.annotation.Transactional;
import com.istad.chanda.payroll.features.employee.EmployeeRepository;
import com.istad.chanda.payroll.features.componenttype.ComponentTypeRepository;
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
public class PayAdjustmentServiceImpl implements PayAdjustmentService {
    private final PayAdjustmentRepository repository;
    private final PayAdjustmentMapper mapper;
    private final EmployeeRepository employeeRepository;
    private final ComponentTypeRepository componenttypeRepository;

    @Override
    @Transactional(readOnly = true)
    public PayAdjustmentResponse findById(String id) { return mapper.toResponse(find(id)); }

    @Override
    @Transactional(readOnly = true)
    public Page<PayAdjustmentResponse> findAll(Pageable pageable) {
        if (pageable.isUnpaged() || pageable.getPageSize() > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageSize must be between 1 and 100");
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PayAdjustmentResponse> search(String name) {
        String term = name == null ? "" : name.trim();
        return repository.findByReasonContainingIgnoreCaseOrEmployee_FirstNameContainingIgnoreCaseOrEmployee_LastNameContainingIgnoreCase(term, term, term).stream().map(mapper::toResponse).toList();
    }

    @Override
    public PayAdjustmentResponse createNew(CreatePayAdjustmentRequest request) {
        PayAdjustment entity = mapper.toEntity(request);
        beforeSave(entity);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public PayAdjustmentResponse updateById(String id, UpdatePayAdjustmentRequest request) {
        PayAdjustment entity = locked(id);
        mapper.update(request, entity);
        beforeSave(entity);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public void deleteById(String id) {
        PayAdjustment entity = locked(id);
        repository.delete(entity);
        repository.flush();
    }

    private PayAdjustment find(String id) { return repository.findById(id).orElseThrow(() -> missing(id)); }
    private PayAdjustment locked(String id) { return repository.findLockedById(id).orElseThrow(() -> missing(id)); }
    private ResponseStatusException missing(String id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found: " + id);
    }
    private void require(boolean condition, String message) {
        if (!condition) throw new ResponseStatusException(HttpStatus.CONFLICT, message);
    }
    private void beforeSave(PayAdjustment entity) {
        var employee = employeeRepository.findById(entity.getEmployeeId()).orElseThrow(() -> missing(entity.getEmployeeId()));
        var componentType = componenttypeRepository.findById(entity.getComponentTypeId()).orElseThrow(() -> missing(entity.getComponentTypeId()));
    }
}
