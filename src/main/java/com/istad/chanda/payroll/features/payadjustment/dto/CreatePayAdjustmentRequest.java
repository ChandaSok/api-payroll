package com.istad.chanda.payroll.features.payadjustment.dto;

import com.istad.chanda.payroll.features.payadjustment.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePayAdjustmentRequest(
        @NotBlank @Size(max = 36) String employeeId,
        @NotBlank @Size(max = 36) String componentTypeId,
        @NotNull @DecimalMin("0.00") @Digits(integer = 15, fraction = 2) BigDecimal amount,
        @NotBlank @Pattern(regexp = "[A-Z]{3}") String currency,
        @NotNull LocalDate effectiveDate,
        @NotBlank @Size(max = 255) String reason
) {
}
