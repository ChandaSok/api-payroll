package com.istad.chanda.payroll.features.seniority;

import com.istad.chanda.payroll.features.seniority.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SeniorityService {
    SeniorityResponse findById(String id);
    Page<SeniorityResponse> findAll(Pageable pageable);
    List<SeniorityResponse> search(String name);
    SeniorityResponse createNew(CreateSeniorityRequest request);
    SeniorityResponse updateById(String id, UpdateSeniorityRequest request);
    void deleteById(String id);
}
