package com.istad.chanda.payroll.features.payadjustment.dto;

import com.istad.chanda.payroll.features.payadjustment.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PayAdjustmentResponse(
        String id,
        String employeeId,
        String componentTypeId,
        BigDecimal amount,
        String currency,
        LocalDate effectiveDate,
        String reason,
        java.time.Instant createdAt,
        java.time.Instant updatedAt
) { }
