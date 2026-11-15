package com.istad.chanda.payroll.features.payrollsetup.dto;

import com.istad.chanda.payroll.features.payrollsetup.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdatePayrollSetupRequest(
        @NotBlank @Size(max = 36) String employeeId,
        @NotNull @DecimalMin("0.00") @Digits(integer = 15, fraction = 2) BigDecimal baseSalary,
        @NotBlank @Pattern(regexp = "[A-Z]{3}") String currency,
        @NotNull @DecimalMin("1.0") @DecimalMax("31.0") @Digits(integer = 2, fraction = 2) BigDecimal standardDaysPerMonth,
        @NotNull LocalDate effectiveFrom,
         LocalDate effectiveTo,
        @NotNull Boolean active
) {
    @AssertTrue(message = "effectiveTo must be on or after effectiveFrom")
    public boolean isConsistent0() { return effectiveTo == null || effectiveFrom == null || !effectiveTo.isBefore(effectiveFrom); }
}
