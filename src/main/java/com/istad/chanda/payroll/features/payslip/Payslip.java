package com.istad.chanda.payroll.features.payslip;

import com.istad.chanda.payroll.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "payslip", uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id", "payroll_run_id"})})
public class Payslip extends BaseEntity {
    @Column(name = "employee_id", nullable = false, length = 36)
    private String employeeId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", insertable = false, updatable = false, nullable = false)
    private com.istad.chanda.payroll.features.employee.Employee employee;
    @Column(name = "payroll_run_id", nullable = false, length = 36)
    private String payrollRunId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payroll_run_id", insertable = false, updatable = false, nullable = false)
    private com.istad.chanda.payroll.features.payrollrun.PayrollRun payrollRun;
    @Column(name = "base_salary", nullable = false, precision = 17, scale = 2)
    private BigDecimal baseSalary;
    @Column(name = "additional_earnings", nullable = false, precision = 17, scale = 2)
    private BigDecimal additionalEarnings;
    @Column(name = "seniority_amount", nullable = false, precision = 17, scale = 2)
    private BigDecimal seniorityAmount;
    @Column(name = "other_deductions", nullable = false, precision = 17, scale = 2)
    private BigDecimal otherDeductions;
    @Column(name = "salary_tax", nullable = false, precision = 17, scale = 2)
    private BigDecimal salaryTax;
    @Column(name = "employee_nssf", nullable = false, precision = 17, scale = 2)
    private BigDecimal employeeNssf;
    @Column(name = "employer_nssf", nullable = false, precision = 17, scale = 2)
    private BigDecimal employerNssf;
    @Column(nullable = false)
    private String employeeCode;
    @Column(nullable = false, length = 511)
    private String employeeName;
    @Transient
    public BigDecimal getGrossPay() { return baseSalary.add(additionalEarnings).add(seniorityAmount); }
    @Transient
    public BigDecimal getNetPay() { return getGrossPay().subtract(otherDeductions).subtract(salaryTax).subtract(employeeNssf); }
}
