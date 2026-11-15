package com.istad.chanda.payroll.features.payslip.dto;

import com.istad.chanda.payroll.features.payslip.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PayslipResponse(
        String id,
        String employeeId,
        String payrollRunId,
        BigDecimal baseSalary,
        BigDecimal additionalEarnings,
        BigDecimal seniorityAmount,
        BigDecimal otherDeductions,
        BigDecimal salaryTax,
        BigDecimal employeeNssf,
        BigDecimal employerNssf,
        String employeeCode,
        String employeeName,
        BigDecimal grossPay,
        BigDecimal netPay,
        java.time.Instant createdAt,
        java.time.Instant updatedAt
) { }
