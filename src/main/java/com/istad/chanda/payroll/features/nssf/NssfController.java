package com.istad.chanda.payroll.features.nssf;

import com.istad.chanda.payroll.features.nssf.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/nssf")
@RequiredArgsConstructor
public class NssfController {
    private final NssfService nssfService;

    @GetMapping("/{id}")
    public NssfResponse findById(@PathVariable String id) { return nssfService.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) { nssfService.deleteById(id); }

    @PutMapping("/{id}")
    public NssfResponse updateById(@PathVariable String id, @Valid @RequestBody UpdateNssfRequest request) {
        return nssfService.updateById(id, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NssfResponse createNew(@Valid @RequestBody CreateNssfRequest request) {
        return nssfService.createNew(request);
    }

    @GetMapping
    public Page<NssfResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize) {
        if (pageNumber < 0 || pageSize < 1 || pageSize > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageNumber must be nonnegative and pageSize must be between 1 and 100");
        return nssfService.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
    }

    @GetMapping("/search")
    public List<NssfResponse> search(@RequestParam(required = false, defaultValue = "") String name) {
        return nssfService.search(name);
    }
}
