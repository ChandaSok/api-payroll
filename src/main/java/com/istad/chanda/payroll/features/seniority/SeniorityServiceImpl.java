package com.istad.chanda.payroll.features.seniority;

import com.istad.chanda.payroll.features.seniority.dto.*;
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
public class SeniorityServiceImpl implements SeniorityService {
    private final SeniorityRepository repository;
    private final SeniorityMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public SeniorityResponse findById(String id) { return mapper.toResponse(find(id)); }

    @Override
    @Transactional(readOnly = true)
    public Page<SeniorityResponse> findAll(Pageable pageable) {
        if (pageable.isUnpaged() || pageable.getPageSize() > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageSize must be between 1 and 100");
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeniorityResponse> search(String name) {
        String term = name == null ? "" : name.trim();
        return repository.findByNameContainingIgnoreCase(term).stream().map(mapper::toResponse).toList();
    }

    @Override
    public SeniorityResponse createNew(CreateSeniorityRequest request) {
        Seniority entity = mapper.toEntity(request);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public SeniorityResponse updateById(String id, UpdateSeniorityRequest request) {
        Seniority entity = locked(id);
        mapper.update(request, entity);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public void deleteById(String id) {
        Seniority entity = locked(id);
        repository.delete(entity);
        repository.flush();
    }

    private Seniority find(String id) { return repository.findById(id).orElseThrow(() -> missing(id)); }
    private Seniority locked(String id) { return repository.findLockedById(id).orElseThrow(() -> missing(id)); }
    private ResponseStatusException missing(String id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found: " + id);
    }
    private void require(boolean condition, String message) {
        if (!condition) throw new ResponseStatusException(HttpStatus.CONFLICT, message);
    }
    
}
