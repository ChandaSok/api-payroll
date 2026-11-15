package com.istad.chanda.payroll.features.employee;

import com.istad.chanda.payroll.features.employee.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EmployeeService {
    EmployeeResponse findById(String id);
    Page<EmployeeResponse> findAll(Pageable pageable);
    List<EmployeeResponse> search(String name);
    EmployeeResponse createNew(CreateEmployeeRequest request);
    EmployeeResponse updateById(String id, UpdateEmployeeRequest request);
    void deleteById(String id);
}
