package com.istad.chanda.payroll.features.componenttype;

import com.istad.chanda.payroll.features.componenttype.dto.*;
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
public class ComponentTypeServiceImpl implements ComponentTypeService {
    private final ComponentTypeRepository repository;
    private final ComponentTypeMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public ComponentTypeResponse findById(String id) { return mapper.toResponse(find(id)); }

    @Override
    @Transactional(readOnly = true)
    public Page<ComponentTypeResponse> findAll(Pageable pageable) {
        if (pageable.isUnpaged() || pageable.getPageSize() > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageSize must be between 1 and 100");
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComponentTypeResponse> search(String name) {
        String term = name == null ? "" : name.trim();
        return repository.findByNameContainingIgnoreCase(term).stream().map(mapper::toResponse).toList();
    }

    @Override
    public ComponentTypeResponse createNew(CreateComponentTypeRequest request) {
        ComponentType entity = mapper.toEntity(request);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public ComponentTypeResponse updateById(String id, UpdateComponentTypeRequest request) {
        ComponentType entity = locked(id);
        mapper.update(request, entity);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public void deleteById(String id) {
        ComponentType entity = locked(id);
        repository.delete(entity);
        repository.flush();
    }

    private ComponentType find(String id) { return repository.findById(id).orElseThrow(() -> missing(id)); }
    private ComponentType locked(String id) { return repository.findLockedById(id).orElseThrow(() -> missing(id)); }
    private ResponseStatusException missing(String id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found: " + id);
    }
    private void require(boolean condition, String message) {
        if (!condition) throw new ResponseStatusException(HttpStatus.CONFLICT, message);
    }
    
}
