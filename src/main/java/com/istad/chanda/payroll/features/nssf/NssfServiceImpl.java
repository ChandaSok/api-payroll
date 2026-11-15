package com.istad.chanda.payroll.features.nssf;

import com.istad.chanda.payroll.features.nssf.dto.*;
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
public class NssfServiceImpl implements NssfService {
    private final NssfRepository repository;
    private final NssfMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public NssfResponse findById(String id) { return mapper.toResponse(find(id)); }

    @Override
    @Transactional(readOnly = true)
    public Page<NssfResponse> findAll(Pageable pageable) {
        if (pageable.isUnpaged() || pageable.getPageSize() > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageSize must be between 1 and 100");
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NssfResponse> search(String name) {
        String term = name == null ? "" : name.trim();
        return repository.findByNameContainingIgnoreCase(term).stream().map(mapper::toResponse).toList();
    }

    @Override
    public NssfResponse createNew(CreateNssfRequest request) {
        Nssf entity = mapper.toEntity(request);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public NssfResponse updateById(String id, UpdateNssfRequest request) {
        Nssf entity = locked(id);
        mapper.update(request, entity);
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Override
    public void deleteById(String id) {
        Nssf entity = locked(id);
        repository.delete(entity);
        repository.flush();
    }

    private Nssf find(String id) { return repository.findById(id).orElseThrow(() -> missing(id)); }
    private Nssf locked(String id) { return repository.findLockedById(id).orElseThrow(() -> missing(id)); }
    private ResponseStatusException missing(String id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found: " + id);
    }
    private void require(boolean condition, String message) {
        if (!condition) throw new ResponseStatusException(HttpStatus.CONFLICT, message);
    }
    
}
