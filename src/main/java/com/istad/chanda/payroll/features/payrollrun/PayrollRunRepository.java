package com.istad.chanda.payroll.features.payrollrun;

import com.istad.chanda.payroll.common.CrudRepository;

public interface PayrollRunRepository extends CrudRepository<PayrollRun> {
    java.util.List<PayrollRun> findByNameContainingIgnoreCase(String term0);
}
