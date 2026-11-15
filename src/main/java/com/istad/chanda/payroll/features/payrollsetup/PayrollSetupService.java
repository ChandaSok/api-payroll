package com.istad.chanda.payroll.features.payrollsetup;

import com.istad.chanda.payroll.features.payrollsetup.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PayrollSetupService {
    PayrollSetupResponse findById(String id);
    Page<PayrollSetupResponse> findAll(Pageable pageable);
    List<PayrollSetupResponse> search(String name);
    PayrollSetupResponse createNew(CreatePayrollSetupRequest request);
    PayrollSetupResponse updateById(String id, UpdatePayrollSetupRequest request);
    void deleteById(String id);
}
