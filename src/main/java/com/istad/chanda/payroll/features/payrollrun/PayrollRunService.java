package com.istad.chanda.payroll.features.payrollrun;

import com.istad.chanda.payroll.features.payrollrun.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PayrollRunService {
    PayrollRunResponse findById(String id);
    Page<PayrollRunResponse> findAll(Pageable pageable);
    List<PayrollRunResponse> search(String name);
    PayrollRunResponse createNew(CreatePayrollRunRequest request);
    PayrollRunResponse updateById(String id, UpdatePayrollRunRequest request);
    void deleteById(String id);
    PayrollRunResponse finalizeRun(String id);
}
