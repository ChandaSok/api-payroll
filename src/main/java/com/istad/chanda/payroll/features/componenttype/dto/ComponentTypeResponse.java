package com.istad.chanda.payroll.features.componenttype.dto;

import com.istad.chanda.payroll.features.componenttype.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ComponentTypeResponse(
        String id,
        String code,
        String name,
        ComponentKind kind,
        Boolean taxable,
        Boolean active,
        java.time.Instant createdAt,
        java.time.Instant updatedAt
) { }
