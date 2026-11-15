package com.istad.chanda.payroll.features.payrollrun;

import com.istad.chanda.payroll.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "payroll_run")
public class PayrollRun extends BaseEntity {
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart;
    @Column(name = "period_end", nullable = false)
    private LocalDate periodEnd;
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayrollRunStatus status = PayrollRunStatus.DRAFT;
}
