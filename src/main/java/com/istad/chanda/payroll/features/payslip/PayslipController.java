package com.istad.chanda.payroll.features.payslip;

import com.istad.chanda.payroll.features.payslip.dto.*;
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
@RequestMapping("/api/v1/payslips")
@RequiredArgsConstructor
public class PayslipController {
    private final PayslipService payslipService;

    @GetMapping("/{id}")
    public PayslipResponse findById(@PathVariable String id) { return payslipService.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) { payslipService.deleteById(id); }

    @PutMapping("/{id}")
    public PayslipResponse updateById(@PathVariable String id, @Valid @RequestBody UpdatePayslipRequest request) {
        return payslipService.updateById(id, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PayslipResponse createNew(@Valid @RequestBody CreatePayslipRequest request) {
        return payslipService.createNew(request);
    }

    @GetMapping
    public Page<PayslipResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize) {
        if (pageNumber < 0 || pageSize < 1 || pageSize > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageNumber must be nonnegative and pageSize must be between 1 and 100");
        return payslipService.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
    }

    @GetMapping("/search")
    public List<PayslipResponse> search(@RequestParam(required = false, defaultValue = "") String name) {
        return payslipService.search(name);
    }
}
