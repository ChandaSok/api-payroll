package com.istad.chanda.payroll.features.payslip;

import com.istad.chanda.payroll.features.payslip.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PayslipService {
    PayslipResponse findById(String id);
    Page<PayslipResponse> findAll(Pageable pageable);
    List<PayslipResponse> search(String name);
    PayslipResponse createNew(CreatePayslipRequest request);
    PayslipResponse updateById(String id, UpdatePayslipRequest request);
    void deleteById(String id);
}
