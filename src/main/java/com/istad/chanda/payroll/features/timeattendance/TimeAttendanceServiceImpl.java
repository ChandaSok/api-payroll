package com.istad.chanda.payroll.features.timeattendance;

import com.istad.chanda.payroll.features.timeattendance.dto.*;
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
public class TimeAttendanceServiceImpl implements TimeAttendanceService {
    private final TimeAttendanceRepository repository;
    private final TimeAttendanceMapper mapper;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public TimeAttendanceResponse findById(String id) { return mapper.toResponse(find(id)); }

    @Override
    @Transactional(readOnly = true)
    public Page<TimeAttendanceResponse> findAll(Pageable pageable) {
        if (pageable.isUnpaged() || pageable.getPageSize() > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageSize must be between 1 and 100");
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeAttendanceResponse> search(String name) {
        String term = name == null ? "" : name.trim();
        return repository.findByEmployee_FirstNameContainingIgnoreCaseOrEmployee_LastNameContainingIgnoreCaseOrEmployee_EmployeeCodeContainingIgnoreCase(term, term, term).stream().map(mapper::toResponse).toList();
    }

    @Override
    public TimeAttendanceResponse createNew(CreateTimeAttendanceRequest request) {
        TimeAttendance entity = mapper.toEntity(request);
        beforeSave(entity);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public TimeAttendanceResponse updateById(String id, UpdateTimeAttendanceRequest request) {
        TimeAttendance entity = locked(id);
        mapper.update(request, entity);
        beforeSave(entity);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public void deleteById(String id) {
        TimeAttendance entity = locked(id);
        repository.delete(entity);
        repository.flush();
    }

    private TimeAttendance find(String id) { return repository.findById(id).orElseThrow(() -> missing(id)); }
    private TimeAttendance locked(String id) { return repository.findLockedById(id).orElseThrow(() -> missing(id)); }
    private ResponseStatusException missing(String id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found: " + id);
    }
    private void require(boolean condition, String message) {
        if (!condition) throw new ResponseStatusException(HttpStatus.CONFLICT, message);
    }
    private void beforeSave(TimeAttendance entity) {
        var employee = employeeRepository.findById(entity.getEmployeeId()).orElseThrow(() -> missing(entity.getEmployeeId()));
    }
}
