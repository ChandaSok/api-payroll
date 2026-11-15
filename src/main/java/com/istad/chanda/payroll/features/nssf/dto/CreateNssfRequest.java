package com.istad.chanda.payroll.features.nssf.dto;

import com.istad.chanda.payroll.features.nssf.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateNssfRequest(
        @NotBlank @Size(max = 255) String name,
        @NotBlank @Pattern(regexp = "[A-Z]{3}") String currency,
        @NotNull @DecimalMin("0.00") @Digits(integer = 15, fraction = 2) BigDecimal minimumBase,
        @NotNull @DecimalMin("0.00") @Digits(integer = 15, fraction = 2) BigDecimal maximumBase,
        @NotNull @DecimalMin("0.0") @DecimalMax("1.0") @Digits(integer = 1, fraction = 6) BigDecimal employeeRate,
        @NotNull @DecimalMin("0.0") @DecimalMax("1.0") @Digits(integer = 1, fraction = 6) BigDecimal employerRate,
        @NotNull LocalDate effectiveFrom,
         LocalDate effectiveTo
) {
    @AssertTrue(message = "effectiveTo must be on or after effectiveFrom")
    public boolean isConsistent0() { return effectiveTo == null || effectiveFrom == null || !effectiveTo.isBefore(effectiveFrom); }
    @AssertTrue(message = "maximumBase must be at least minimumBase")
    public boolean isConsistent1() { return minimumBase == null || maximumBase == null || maximumBase.compareTo(minimumBase) >= 0; }
}
