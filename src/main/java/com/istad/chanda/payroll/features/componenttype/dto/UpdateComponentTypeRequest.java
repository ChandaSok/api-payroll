package com.istad.chanda.payroll.features.componenttype.dto;

import com.istad.chanda.payroll.features.componenttype.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateComponentTypeRequest(
        @NotBlank @Size(max = 255) String code,
        @NotBlank @Size(max = 255) String name,
        @NotNull ComponentKind kind,
        @NotNull Boolean taxable,
        @NotNull Boolean active
) {
}
