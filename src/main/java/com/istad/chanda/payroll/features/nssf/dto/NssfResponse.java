package com.istad.chanda.payroll.features.nssf.dto;

import com.istad.chanda.payroll.features.nssf.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record NssfResponse(
        String id,
        String name,
        String currency,
        BigDecimal minimumBase,
        BigDecimal maximumBase,
        BigDecimal employeeRate,
        BigDecimal employerRate,
        LocalDate effectiveFrom,
        LocalDate effectiveTo,
        java.time.Instant createdAt,
        java.time.Instant updatedAt
) { }
