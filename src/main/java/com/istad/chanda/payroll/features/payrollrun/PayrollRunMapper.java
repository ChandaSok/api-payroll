package com.istad.chanda.payroll.features.payrollrun;

import com.istad.chanda.payroll.features.payrollrun.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PayrollRunMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    PayrollRun toEntity(CreatePayrollRunRequest request);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    void update(UpdatePayrollRunRequest request, @MappingTarget PayrollRun entity);
    PayrollRunResponse toResponse(PayrollRun entity);
}
