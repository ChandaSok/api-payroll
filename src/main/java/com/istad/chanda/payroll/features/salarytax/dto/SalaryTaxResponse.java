package com.istad.chanda.payroll.features.salarytax.dto;

import com.istad.chanda.payroll.features.salarytax.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record SalaryTaxResponse(
        String id,
        String name,
        String currency,
        BigDecimal lowerBound,
        BigDecimal upperBound,
        BigDecimal rate,
        BigDecimal deduction,
        LocalDate effectiveFrom,
        LocalDate effectiveTo,
        java.time.Instant createdAt,
        java.time.Instant updatedAt
) { }
