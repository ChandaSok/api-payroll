package com.istad.chanda.payroll.features.payrollsetup.dto;

import com.istad.chanda.payroll.features.payrollsetup.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PayrollSetupResponse(
        String id,
        String employeeId,
        BigDecimal baseSalary,
        String currency,
        BigDecimal standardDaysPerMonth,
        LocalDate effectiveFrom,
        LocalDate effectiveTo,
        Boolean active,
        java.time.Instant createdAt,
        java.time.Instant updatedAt
) { }
