package com.istad.chanda.payroll.features.payrollrun.dto;

import com.istad.chanda.payroll.features.payrollrun.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePayrollRunRequest(
        @NotBlank @Size(max = 255) String name,
        @NotNull LocalDate periodStart,
        @NotNull LocalDate periodEnd,
        @NotNull LocalDate paymentDate,
        @NotBlank @Pattern(regexp = "[A-Z]{3}") String currency
) {
    @AssertTrue(message = "periodEnd must be on or after periodStart")
    public boolean isConsistent0() { return periodEnd == null || periodStart == null || !periodEnd.isBefore(periodStart); }
}
