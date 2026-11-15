package com.istad.chanda.payroll.features.exchangerate;

import com.istad.chanda.payroll.features.exchangerate.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ExchangeRateService {
    ExchangeRateResponse findById(String id);
    Page<ExchangeRateResponse> findAll(Pageable pageable);
    List<ExchangeRateResponse> search(String name);
    ExchangeRateResponse createNew(CreateExchangeRateRequest request);
    ExchangeRateResponse updateById(String id, UpdateExchangeRateRequest request);
    void deleteById(String id);
}
