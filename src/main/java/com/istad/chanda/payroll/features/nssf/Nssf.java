package com.istad.chanda.payroll.features.nssf;

import com.istad.chanda.payroll.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "nssf")
public class Nssf extends BaseEntity {
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;
    @Column(name = "minimum_base", nullable = false, precision = 17, scale = 2)
    private BigDecimal minimumBase;
    @Column(name = "maximum_base", nullable = false, precision = 17, scale = 2)
    private BigDecimal maximumBase;
    @Column(name = "employee_rate", nullable = false, precision = 7, scale = 6)
    private BigDecimal employeeRate;
    @Column(name = "employer_rate", nullable = false, precision = 7, scale = 6)
    private BigDecimal employerRate;
    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;
    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;
}
