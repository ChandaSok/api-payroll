package com.istad.chanda.payroll.features.exchangerate.dto;

import com.istad.chanda.payroll.features.exchangerate.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ExchangeRateResponse(
        String id,
        String baseCurrency,
        String quoteCurrency,
        BigDecimal rate,
        LocalDate effectiveDate,
        java.time.Instant createdAt,
        java.time.Instant updatedAt
) { }
