package com.istad.chanda.payroll.features.payadjustment;

import com.istad.chanda.payroll.features.payadjustment.dto.*;
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
@RequestMapping("/api/v1/pay-adjustments")
@RequiredArgsConstructor
public class PayAdjustmentController {
    private final PayAdjustmentService payAdjustmentService;

    @GetMapping("/{id}")
    public PayAdjustmentResponse findById(@PathVariable String id) { return payAdjustmentService.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) { payAdjustmentService.deleteById(id); }

    @PutMapping("/{id}")
    public PayAdjustmentResponse updateById(@PathVariable String id, @Valid @RequestBody UpdatePayAdjustmentRequest request) {
        return payAdjustmentService.updateById(id, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PayAdjustmentResponse createNew(@Valid @RequestBody CreatePayAdjustmentRequest request) {
        return payAdjustmentService.createNew(request);
    }

    @GetMapping
    public Page<PayAdjustmentResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize) {
        if (pageNumber < 0 || pageSize < 1 || pageSize > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageNumber must be nonnegative and pageSize must be between 1 and 100");
        return payAdjustmentService.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
    }

    @GetMapping("/search")
    public List<PayAdjustmentResponse> search(@RequestParam(required = false, defaultValue = "") String name) {
        return payAdjustmentService.search(name);
    }
}
