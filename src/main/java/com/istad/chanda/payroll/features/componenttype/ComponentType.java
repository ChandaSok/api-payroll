package com.istad.chanda.payroll.features.componenttype;

import com.istad.chanda.payroll.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "component_type", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
public class ComponentType extends BaseEntity {
    @Column(name = "code", nullable = false, length = 255)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "kind", nullable = false)
    @Enumerated(EnumType.STRING)
    private ComponentKind kind;

    @Column(name = "taxable", nullable = false)
    private Boolean taxable;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
