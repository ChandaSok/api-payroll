package com.istad.chanda.payroll.features.componenttype;

import com.istad.chanda.payroll.features.componenttype.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ComponentTypeService {
    ComponentTypeResponse findById(String id);
    Page<ComponentTypeResponse> findAll(Pageable pageable);
    List<ComponentTypeResponse> search(String name);
    ComponentTypeResponse createNew(CreateComponentTypeRequest request);
    ComponentTypeResponse updateById(String id, UpdateComponentTypeRequest request);
    void deleteById(String id);
}
