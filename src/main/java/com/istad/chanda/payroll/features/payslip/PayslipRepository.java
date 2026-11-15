package com.istad.chanda.payroll.features.payslip;

import com.istad.chanda.payroll.common.CrudRepository;

public interface PayslipRepository extends CrudRepository<Payslip> {
    boolean existsByPayrollRunId(String payrollRunId);
    java.util.List<Payslip> findAllByPayrollRunId(String payrollRunId);
    java.util.List<Payslip> findByEmployeeNameContainingIgnoreCaseOrEmployeeCodeContainingIgnoreCase(String term0, String term1);
}
