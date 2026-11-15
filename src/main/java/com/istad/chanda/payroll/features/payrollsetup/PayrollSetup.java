package com.istad.chanda.payroll.features.payrollsetup;

import com.istad.chanda.payroll.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "payroll_setup", uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id", "effective_from"})})
public class PayrollSetup extends BaseEntity {
    @Column(name = "employee_id", nullable = false, length = 36)
    private String employeeId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", insertable = false, updatable = false, nullable = false)
    private com.istad.chanda.payroll.features.employee.Employee employee;
    @Column(name = "base_salary", nullable = false, precision = 17, scale = 2)
    private BigDecimal baseSalary;
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;
    @Column(name = "standard_days_per_month", nullable = false, precision = 17, scale = 2)
    private BigDecimal standardDaysPerMonth;
    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;
    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;
    @Column(name = "active", nullable = false)
    private Boolean active;
}
