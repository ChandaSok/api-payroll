package com.istad.chanda.payroll.features.employee;

import com.istad.chanda.payroll.features.employee.dto.*;
import org.springframework.transaction.annotation.Transactional;
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
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse findById(String id) { return mapper.toResponse(find(id)); }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> findAll(Pageable pageable) {
        if (pageable.isUnpaged() || pageable.getPageSize() > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageSize must be between 1 and 100");
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> search(String name) {
        String term = name == null ? "" : name.trim();
        return repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmployeeCodeContainingIgnoreCase(term, term, term).stream().map(mapper::toResponse).toList();
    }

    @Override
    public EmployeeResponse createNew(CreateEmployeeRequest request) {
        Employee entity = mapper.toEntity(request);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public EmployeeResponse updateById(String id, UpdateEmployeeRequest request) {
        Employee entity = locked(id);
        mapper.update(request, entity);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public void deleteById(String id) {
        Employee entity = locked(id);
        repository.delete(entity);
        repository.flush();
    }

    private Employee find(String id) { return repository.findById(id).orElseThrow(() -> missing(id)); }
    private Employee locked(String id) { return repository.findLockedById(id).orElseThrow(() -> missing(id)); }
    private ResponseStatusException missing(String id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found: " + id);
    }
    private void require(boolean condition, String message) {
        if (!condition) throw new ResponseStatusException(HttpStatus.CONFLICT, message);
    }
    
}
