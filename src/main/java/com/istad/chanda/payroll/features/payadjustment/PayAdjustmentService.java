package com.istad.chanda.payroll.features.payadjustment;

import com.istad.chanda.payroll.features.payadjustment.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PayAdjustmentService {
    PayAdjustmentResponse findById(String id);
    Page<PayAdjustmentResponse> findAll(Pageable pageable);
    List<PayAdjustmentResponse> search(String name);
    PayAdjustmentResponse createNew(CreatePayAdjustmentRequest request);
    PayAdjustmentResponse updateById(String id, UpdatePayAdjustmentRequest request);
    void deleteById(String id);
}
