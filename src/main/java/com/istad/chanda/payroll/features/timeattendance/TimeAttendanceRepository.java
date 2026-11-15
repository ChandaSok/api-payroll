package com.istad.chanda.payroll.features.timeattendance;

import com.istad.chanda.payroll.common.CrudRepository;

public interface TimeAttendanceRepository extends CrudRepository<TimeAttendance> {
    java.util.List<TimeAttendance> findByEmployee_FirstNameContainingIgnoreCaseOrEmployee_LastNameContainingIgnoreCaseOrEmployee_EmployeeCodeContainingIgnoreCase(String term0, String term1, String term2);
}
