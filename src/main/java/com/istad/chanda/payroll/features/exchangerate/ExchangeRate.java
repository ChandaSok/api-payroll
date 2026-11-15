package com.istad.chanda.payroll.features.exchangerate;

import com.istad.chanda.payroll.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "exchange_rate", uniqueConstraints = {@UniqueConstraint(columnNames = {"base_currency", "quote_currency", "effective_date"})})
public class ExchangeRate extends BaseEntity {
    @Column(name = "base_currency", nullable = false, length = 3)
    private String baseCurrency;
    @Column(name = "quote_currency", nullable = false, length = 3)
    private String quoteCurrency;
    @Column(name = "rate", nullable = false, precision = 20, scale = 8)
    private BigDecimal rate;
    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;
}
