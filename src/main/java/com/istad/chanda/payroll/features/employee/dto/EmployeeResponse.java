package com.istad.chanda.payroll.features.employee.dto;

import com.istad.chanda.payroll.features.employee.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeResponse(
        String id,
        String employeeCode,
        String firstName,
        String lastName,
        String email,
        String department,
        String jobTitle,
        LocalDate hireDate,
        LocalDate terminationDate,
        Boolean active,
        java.time.Instant createdAt,
        java.time.Instant updatedAt
) { }
