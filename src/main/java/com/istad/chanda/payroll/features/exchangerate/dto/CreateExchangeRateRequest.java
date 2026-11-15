package com.istad.chanda.payroll.features.exchangerate.dto;

import com.istad.chanda.payroll.features.exchangerate.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateExchangeRateRequest(
        @NotBlank @Pattern(regexp = "[A-Z]{3}") String baseCurrency,
        @NotBlank @Pattern(regexp = "[A-Z]{3}") String quoteCurrency,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 12, fraction = 8) BigDecimal rate,
        @NotNull LocalDate effectiveDate
) {
    @AssertTrue(message = "currencies must differ")
    public boolean isConsistent0() { return baseCurrency == null || !baseCurrency.equals(quoteCurrency); }
}
