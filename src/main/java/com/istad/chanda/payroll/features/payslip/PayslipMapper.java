package com.istad.chanda.payroll.features.payslip;

import com.istad.chanda.payroll.features.payslip.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PayslipMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "payrollRun", ignore = true)
    @Mapping(target = "employeeCode", ignore = true)
    @Mapping(target = "employeeName", ignore = true)
    Payslip toEntity(CreatePayslipRequest request);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "payrollRun", ignore = true)
    @Mapping(target = "employeeCode", ignore = true)
    @Mapping(target = "employeeName", ignore = true)
    void update(UpdatePayslipRequest request, @MappingTarget Payslip entity);
    PayslipResponse toResponse(Payslip entity);
}
