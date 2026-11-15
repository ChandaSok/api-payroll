package com.istad.chanda.payroll.features.seniority.dto;

import com.istad.chanda.payroll.features.seniority.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record SeniorityResponse(
        String id,
        String name,
        Integer minimumServiceMonths,
        BigDecimal daysPerYear,
        LocalDate effectiveFrom,
        LocalDate effectiveTo,
        Boolean active,
        java.time.Instant createdAt,
        java.time.Instant updatedAt
) { }
