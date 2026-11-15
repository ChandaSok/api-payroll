package com.istad.chanda.payroll.features.seniority.dto;

import com.istad.chanda.payroll.features.seniority.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateSeniorityRequest(
        @NotBlank @Size(max = 255) String name,
        @NotNull @Min(0) Integer minimumServiceMonths,
        @NotNull @DecimalMin("0.0") @DecimalMax("366.0") @Digits(integer = 3, fraction = 2) BigDecimal daysPerYear,
        @NotNull LocalDate effectiveFrom,
         LocalDate effectiveTo,
        @NotNull Boolean active
) {
    @AssertTrue(message = "effectiveTo must be on or after effectiveFrom")
    public boolean isConsistent0() { return effectiveTo == null || effectiveFrom == null || !effectiveTo.isBefore(effectiveFrom); }
}
