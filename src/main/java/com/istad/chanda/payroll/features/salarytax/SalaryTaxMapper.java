package com.istad.chanda.payroll.features.salarytax;

import com.istad.chanda.payroll.features.salarytax.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SalaryTaxMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SalaryTax toEntity(CreateSalaryTaxRequest request);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void update(UpdateSalaryTaxRequest request, @MappingTarget SalaryTax entity);
    SalaryTaxResponse toResponse(SalaryTax entity);
}
