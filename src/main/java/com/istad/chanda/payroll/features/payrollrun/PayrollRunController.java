package com.istad.chanda.payroll.features.payrollrun;

import com.istad.chanda.payroll.features.payrollrun.dto.*;
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
@RequestMapping("/api/v1/payroll-runs")
@RequiredArgsConstructor
public class PayrollRunController {
    private final PayrollRunService payrollRunService;

    @GetMapping("/{id}")
    public PayrollRunResponse findById(@PathVariable String id) { return payrollRunService.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) { payrollRunService.deleteById(id); }

    @PutMapping("/{id}")
    public PayrollRunResponse updateById(@PathVariable String id, @Valid @RequestBody UpdatePayrollRunRequest request) {
        return payrollRunService.updateById(id, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PayrollRunResponse createNew(@Valid @RequestBody CreatePayrollRunRequest request) {
        return payrollRunService.createNew(request);
    }

    @GetMapping
    public Page<PayrollRunResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize) {
        if (pageNumber < 0 || pageSize < 1 || pageSize > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageNumber must be nonnegative and pageSize must be between 1 and 100");
        return payrollRunService.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
    }

    @GetMapping("/search")
    public List<PayrollRunResponse> search(@RequestParam(required = false, defaultValue = "") String name) {
        return payrollRunService.search(name);
    }

    @PostMapping("/{id}/finalize")
    public PayrollRunResponse finalizeRun(@PathVariable String id) { return payrollRunService.finalizeRun(id); }
}
