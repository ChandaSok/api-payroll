package com.istad.chanda.payroll.features.employee.dto;

import com.istad.chanda.payroll.features.employee.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateEmployeeRequest(
        @NotBlank @Size(max = 255) String employeeCode,
        @NotBlank @Size(max = 255) String firstName,
        @NotBlank @Size(max = 255) String lastName,
        @NotBlank @Email @Size(max = 255) String email,
        @NotBlank @Size(max = 255) String department,
        @NotBlank @Size(max = 255) String jobTitle,
        @NotNull LocalDate hireDate,
         LocalDate terminationDate,
        @NotNull Boolean active
) {
    @AssertTrue(message = "terminationDate must be on or after hireDate")
    public boolean isConsistent0() { return terminationDate == null || hireDate == null || !terminationDate.isBefore(hireDate); }
}
