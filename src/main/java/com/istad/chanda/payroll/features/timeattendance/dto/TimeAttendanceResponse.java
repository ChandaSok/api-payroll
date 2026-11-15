package com.istad.chanda.payroll.features.timeattendance.dto;

import com.istad.chanda.payroll.features.timeattendance.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TimeAttendanceResponse(
        String id,
        String employeeId,
        LocalDate workDate,
        BigDecimal workedHours,
        BigDecimal overtimeHours,
        AttendanceStatus status,
        java.time.Instant createdAt,
        java.time.Instant updatedAt
) { }
