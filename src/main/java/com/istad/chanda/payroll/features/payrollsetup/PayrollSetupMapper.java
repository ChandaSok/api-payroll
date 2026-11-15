package com.istad.chanda.payroll.features.payrollsetup;

import com.istad.chanda.payroll.features.payrollsetup.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PayrollSetupMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "employee", ignore = true)
    PayrollSetup toEntity(CreatePayrollSetupRequest request);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "employee", ignore = true)
    void update(UpdatePayrollSetupRequest request, @MappingTarget PayrollSetup entity);
    PayrollSetupResponse toResponse(PayrollSetup entity);
}
