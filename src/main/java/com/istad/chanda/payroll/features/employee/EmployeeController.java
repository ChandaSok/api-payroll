package com.istad.chanda.payroll.features.employee;

import com.istad.chanda.payroll.features.employee.dto.*;
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
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public EmployeeResponse findById(@PathVariable String id) { return employeeService.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) { employeeService.deleteById(id); }

    @PutMapping("/{id}")
    public EmployeeResponse updateById(@PathVariable String id, @Valid @RequestBody UpdateEmployeeRequest request) {
        return employeeService.updateById(id, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse createNew(@Valid @RequestBody CreateEmployeeRequest request) {
        return employeeService.createNew(request);
    }

    @GetMapping
    public Page<EmployeeResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize) {
        if (pageNumber < 0 || pageSize < 1 || pageSize > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageNumber must be nonnegative and pageSize must be between 1 and 100");
        return employeeService.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
    }

    @GetMapping("/search")
    public List<EmployeeResponse> search(@RequestParam(required = false, defaultValue = "") String name) {
        return employeeService.search(name);
    }
}
