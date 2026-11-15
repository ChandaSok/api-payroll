package com.istad.chanda.payroll.features.payadjustment;

import com.istad.chanda.payroll.features.payadjustment.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PayAdjustmentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "componentType", ignore = true)
    PayAdjustment toEntity(CreatePayAdjustmentRequest request);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "componentType", ignore = true)
    void update(UpdatePayAdjustmentRequest request, @MappingTarget PayAdjustment entity);
    PayAdjustmentResponse toResponse(PayAdjustment entity);
}
