package com.istad.chanda.payroll.features.seniority;

import com.istad.chanda.payroll.features.seniority.dto.*;
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
@RequestMapping("/api/v1/seniorities")
@RequiredArgsConstructor
public class SeniorityController {
    private final SeniorityService seniorityService;

    @GetMapping("/{id}")
    public SeniorityResponse findById(@PathVariable String id) { return seniorityService.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) { seniorityService.deleteById(id); }

    @PutMapping("/{id}")
    public SeniorityResponse updateById(@PathVariable String id, @Valid @RequestBody UpdateSeniorityRequest request) {
        return seniorityService.updateById(id, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SeniorityResponse createNew(@Valid @RequestBody CreateSeniorityRequest request) {
        return seniorityService.createNew(request);
    }

    @GetMapping
    public Page<SeniorityResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize) {
        if (pageNumber < 0 || pageSize < 1 || pageSize > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageNumber must be nonnegative and pageSize must be between 1 and 100");
        return seniorityService.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
    }

    @GetMapping("/search")
    public List<SeniorityResponse> search(@RequestParam(required = false, defaultValue = "") String name) {
        return seniorityService.search(name);
    }
}
