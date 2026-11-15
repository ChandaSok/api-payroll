package com.istad.chanda.payroll.features.componenttype;

import com.istad.chanda.payroll.features.componenttype.dto.*;
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
@RequestMapping({"/api/v1/component-types", "/api/v1/component_type"})
@RequiredArgsConstructor
public class ComponentTypeController {
    private final ComponentTypeService componentTypeService;

    @GetMapping("/{id}")
    public ComponentTypeResponse findById(@PathVariable String id) { return componentTypeService.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) { componentTypeService.deleteById(id); }

    @PutMapping("/{id}")
    public ComponentTypeResponse updateById(@PathVariable String id, @Valid @RequestBody UpdateComponentTypeRequest request) {
        return componentTypeService.updateById(id, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComponentTypeResponse createNew(@Valid @RequestBody CreateComponentTypeRequest request) {
        return componentTypeService.createNew(request);
    }

    @GetMapping
    public Page<ComponentTypeResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize) {
        if (pageNumber < 0 || pageSize < 1 || pageSize > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageNumber must be nonnegative and pageSize must be between 1 and 100");
        return componentTypeService.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
    }

    @GetMapping("/search")
    public List<ComponentTypeResponse> search(@RequestParam(required = false, defaultValue = "") String name) {
        return componentTypeService.search(name);
    }
}
