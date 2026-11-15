package com.istad.chanda.payroll.features.timeattendance;

import com.istad.chanda.payroll.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "time_attendance", uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id", "work_date"})})
public class TimeAttendance extends BaseEntity {
    @Column(name = "employee_id", nullable = false, length = 36)
    private String employeeId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", insertable = false, updatable = false, nullable = false)
    private com.istad.chanda.payroll.features.employee.Employee employee;
    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;
    @Column(name = "worked_hours", nullable = false, precision = 17, scale = 2)
    private BigDecimal workedHours;
    @Column(name = "overtime_hours", nullable = false, precision = 17, scale = 2)
    private BigDecimal overtimeHours;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}
