package com.istad.chanda.payroll.features.salarytax;

import com.istad.chanda.payroll.features.salarytax.dto.*;
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
@RequestMapping("/api/v1/salary-taxes")
@RequiredArgsConstructor
public class SalaryTaxController {
    private final SalaryTaxService salaryTaxService;

    @GetMapping("/{id}")
    public SalaryTaxResponse findById(@PathVariable String id) { return salaryTaxService.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) { salaryTaxService.deleteById(id); }

    @PutMapping("/{id}")
    public SalaryTaxResponse updateById(@PathVariable String id, @Valid @RequestBody UpdateSalaryTaxRequest request) {
        return salaryTaxService.updateById(id, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryTaxResponse createNew(@Valid @RequestBody CreateSalaryTaxRequest request) {
        return salaryTaxService.createNew(request);
    }

    @GetMapping
    public Page<SalaryTaxResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize) {
        if (pageNumber < 0 || pageSize < 1 || pageSize > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageNumber must be nonnegative and pageSize must be between 1 and 100");
        return salaryTaxService.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
    }

    @GetMapping("/search")
    public List<SalaryTaxResponse> search(@RequestParam(required = false, defaultValue = "") String name) {
        return salaryTaxService.search(name);
    }
}
