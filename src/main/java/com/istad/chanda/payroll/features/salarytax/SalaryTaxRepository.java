package com.istad.chanda.payroll.features.salarytax;

import com.istad.chanda.payroll.common.CrudRepository;

public interface SalaryTaxRepository extends CrudRepository<SalaryTax> {
    java.util.List<SalaryTax> findByNameContainingIgnoreCase(String term0);
}
