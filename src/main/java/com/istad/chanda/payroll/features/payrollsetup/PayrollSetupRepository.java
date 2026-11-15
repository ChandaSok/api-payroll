package com.istad.chanda.payroll.features.payrollsetup;

import com.istad.chanda.payroll.common.CrudRepository;

public interface PayrollSetupRepository extends CrudRepository<PayrollSetup> {
    java.util.List<PayrollSetup> findByEmployee_FirstNameContainingIgnoreCaseOrEmployee_LastNameContainingIgnoreCaseOrEmployee_EmployeeCodeContainingIgnoreCase(String term0, String term1, String term2);
}
