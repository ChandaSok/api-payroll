package com.istad.chanda.payroll.features.payslip.dto;

import com.istad.chanda.payroll.features.payslip.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePayslipRequest(
        @NotBlank @Size(max = 36) String employeeId,
        @NotBlank @Size(max = 36) String payrollRunId,
        @NotNull @DecimalMin("0.00") @Digits(integer = 15, fraction = 2) BigDecimal baseSalary,
        @NotNull @DecimalMin("0.00") @Digits(integer = 15, fraction = 2) BigDecimal additionalEarnings,
        @NotNull @DecimalMin("0.00") @Digits(integer = 15, fraction = 2) BigDecimal seniorityAmount,
        @NotNull @DecimalMin("0.00") @Digits(integer = 15, fraction = 2) BigDecimal otherDeductions,
        @NotNull @DecimalMin("0.00") @Digits(integer = 15, fraction = 2) BigDecimal salaryTax,
        @NotNull @DecimalMin("0.00") @Digits(integer = 15, fraction = 2) BigDecimal employeeNssf,
        @NotNull @DecimalMin("0.00") @Digits(integer = 15, fraction = 2) BigDecimal employerNssf
) {
}
