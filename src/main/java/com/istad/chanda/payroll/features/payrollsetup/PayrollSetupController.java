package com.istad.chanda.payroll.features.payrollsetup;

import com.istad.chanda.payroll.features.payrollsetup.dto.*;
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
@RequestMapping("/api/v1/payroll-setups")
@RequiredArgsConstructor
public class PayrollSetupController {
    private final PayrollSetupService payrollSetupService;

    @GetMapping("/{id}")
    public PayrollSetupResponse findById(@PathVariable String id) { return payrollSetupService.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) { payrollSetupService.deleteById(id); }

    @PutMapping("/{id}")
    public PayrollSetupResponse updateById(@PathVariable String id, @Valid @RequestBody UpdatePayrollSetupRequest request) {
        return payrollSetupService.updateById(id, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PayrollSetupResponse createNew(@Valid @RequestBody CreatePayrollSetupRequest request) {
        return payrollSetupService.createNew(request);
    }

    @GetMapping
    public Page<PayrollSetupResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize) {
        if (pageNumber < 0 || pageSize < 1 || pageSize > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageNumber must be nonnegative and pageSize must be between 1 and 100");
        return payrollSetupService.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
    }

    @GetMapping("/search")
    public List<PayrollSetupResponse> search(@RequestParam(required = false, defaultValue = "") String name) {
        return payrollSetupService.search(name);
    }
}
