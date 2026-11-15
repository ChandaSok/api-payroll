package com.istad.chanda.payroll.features.timeattendance.dto;

import com.istad.chanda.payroll.features.timeattendance.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTimeAttendanceRequest(
        @NotBlank @Size(max = 36) String employeeId,
        @NotNull LocalDate workDate,
        @NotNull @DecimalMin("0.0") @DecimalMax("24.0") @Digits(integer = 2, fraction = 2) BigDecimal workedHours,
        @NotNull @DecimalMin("0.0") @DecimalMax("24.0") @Digits(integer = 2, fraction = 2) BigDecimal overtimeHours,
        @NotNull AttendanceStatus status
) {
    @AssertTrue(message = "total hours cannot exceed 24")
    public boolean isConsistent0() { return workedHours == null || overtimeHours == null || workedHours.add(overtimeHours).compareTo(new BigDecimal("24")) <= 0; }
    @AssertTrue(message = "non-present attendance must have zero hours")
    public boolean isConsistent1() { return status == null || status == AttendanceStatus.PRESENT || (workedHours != null && overtimeHours != null && workedHours.signum() == 0 && overtimeHours.signum() == 0); }
}
