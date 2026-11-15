package com.istad.chanda.payroll.features.salarytax;

import com.istad.chanda.payroll.features.salarytax.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SalaryTaxService {
    SalaryTaxResponse findById(String id);
    Page<SalaryTaxResponse> findAll(Pageable pageable);
    List<SalaryTaxResponse> search(String name);
    SalaryTaxResponse createNew(CreateSalaryTaxRequest request);
    SalaryTaxResponse updateById(String id, UpdateSalaryTaxRequest request);
    void deleteById(String id);
}
