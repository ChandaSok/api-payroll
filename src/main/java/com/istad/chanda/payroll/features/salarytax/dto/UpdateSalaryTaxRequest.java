package com.istad.chanda.payroll.features.salarytax.dto;

import com.istad.chanda.payroll.features.salarytax.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateSalaryTaxRequest(
        @NotBlank @Size(max = 255) String name,
        @NotBlank @Pattern(regexp = "[A-Z]{3}") String currency,
        @NotNull @DecimalMin("0.00") @Digits(integer = 15, fraction = 2) BigDecimal lowerBound,
        @DecimalMin("0.00") @Digits(integer = 15, fraction = 2) BigDecimal upperBound,
        @NotNull @DecimalMin("0.0") @DecimalMax("1.0") @Digits(integer = 1, fraction = 6) BigDecimal rate,
        @NotNull @DecimalMin("0.00") @Digits(integer = 15, fraction = 2) BigDecimal deduction,
        @NotNull LocalDate effectiveFrom,
         LocalDate effectiveTo
) {
    @AssertTrue(message = "effectiveTo must be on or after effectiveFrom")
    public boolean isConsistent0() { return effectiveTo == null || effectiveFrom == null || !effectiveTo.isBefore(effectiveFrom); }
    @AssertTrue(message = "upperBound must exceed lowerBound")
    public boolean isConsistent1() { return upperBound == null || lowerBound == null || upperBound.compareTo(lowerBound) > 0; }
}
