package com.istad.chanda.payroll.features.payadjustment;

import com.istad.chanda.payroll.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "pay_adjustment")
public class PayAdjustment extends BaseEntity {
    @Column(name = "employee_id", nullable = false, length = 36)
    private String employeeId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", insertable = false, updatable = false, nullable = false)
    private com.istad.chanda.payroll.features.employee.Employee employee;
    @Column(name = "component_type_id", nullable = false, length = 36)
    private String componentTypeId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "component_type_id", insertable = false, updatable = false, nullable = false)
    private com.istad.chanda.payroll.features.componenttype.ComponentType componentType;
    @Column(name = "amount", nullable = false, precision = 17, scale = 2)
    private BigDecimal amount;
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;
    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;
    @Column(name = "reason", nullable = false, length = 255)
    private String reason;
}
