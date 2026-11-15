package com.istad.chanda.payroll.features.exchangerate;

import com.istad.chanda.payroll.features.exchangerate.dto.*;
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
@RequestMapping("/api/v1/exchange-rates")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/{id}")
    public ExchangeRateResponse findById(@PathVariable String id) { return exchangeRateService.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) { exchangeRateService.deleteById(id); }

    @PutMapping("/{id}")
    public ExchangeRateResponse updateById(@PathVariable String id, @Valid @RequestBody UpdateExchangeRateRequest request) {
        return exchangeRateService.updateById(id, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExchangeRateResponse createNew(@Valid @RequestBody CreateExchangeRateRequest request) {
        return exchangeRateService.createNew(request);
    }

    @GetMapping
    public Page<ExchangeRateResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize) {
        if (pageNumber < 0 || pageSize < 1 || pageSize > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageNumber must be nonnegative and pageSize must be between 1 and 100");
        return exchangeRateService.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
    }

    @GetMapping("/search")
    public List<ExchangeRateResponse> search(@RequestParam(required = false, defaultValue = "") String name) {
        return exchangeRateService.search(name);
    }
}
