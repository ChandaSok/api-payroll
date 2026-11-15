package com.istad.chanda.payroll.features.salarytax;

import com.istad.chanda.payroll.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "salary_tax")
public class SalaryTax extends BaseEntity {
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;
    @Column(name = "lower_bound", nullable = false, precision = 17, scale = 2)
    private BigDecimal lowerBound;
    @Column(name = "upper_bound", nullable = true, precision = 17, scale = 2)
    private BigDecimal upperBound;
    @Column(name = "rate", nullable = false, precision = 7, scale = 6)
    private BigDecimal rate;
    @Column(name = "deduction", nullable = false, precision = 17, scale = 2)
    private BigDecimal deduction;
    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;
    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;
}
