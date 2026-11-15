package com.istad.chanda.payroll.features.payrollrun.dto;

import com.istad.chanda.payroll.features.payrollrun.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PayrollRunResponse(
        String id,
        String name,
        LocalDate periodStart,
        LocalDate periodEnd,
        LocalDate paymentDate,
        String currency,
        PayrollRunStatus status,
        java.time.Instant createdAt,
        java.time.Instant updatedAt
) { }
