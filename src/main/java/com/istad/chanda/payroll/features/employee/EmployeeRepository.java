package com.istad.chanda.payroll.features.employee;

import com.istad.chanda.payroll.common.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee> {
    java.util.List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmployeeCodeContainingIgnoreCase(String term0, String term1, String term2);
}
