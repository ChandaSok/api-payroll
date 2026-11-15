package com.istad.chanda.payroll.features.exchangerate;

import com.istad.chanda.payroll.common.CrudRepository;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate> {
    java.util.List<ExchangeRate> findByBaseCurrencyContainingIgnoreCaseOrQuoteCurrencyContainingIgnoreCase(String term0, String term1);
}
