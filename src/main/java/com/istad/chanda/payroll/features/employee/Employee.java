package com.istad.chanda.payroll.features.employee;

import com.istad.chanda.payroll.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employee", uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_code"}), @UniqueConstraint(columnNames = {"email"})})
public class Employee extends BaseEntity {
    @Column(name = "employee_code", nullable = false, length = 255)
    private String employeeCode;
    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @Column(name = "department", nullable = false, length = 255)
    private String department;
    @Column(name = "job_title", nullable = false, length = 255)
    private String jobTitle;
    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;
    @Column(name = "termination_date", nullable = true)
    private LocalDate terminationDate;
    @Column(name = "active", nullable = false)
    private Boolean active;
}
