package com.istad.chanda.payroll.features.seniority;

import com.istad.chanda.payroll.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "seniority")
public class Seniority extends BaseEntity {
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "minimum_service_months", nullable = false)
    private Integer minimumServiceMonths;
    @Column(name = "days_per_year", nullable = false, precision = 17, scale = 2)
    private BigDecimal daysPerYear;
    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;
    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;
    @Column(name = "active", nullable = false)
    private Boolean active;
}
