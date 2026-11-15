package com.istad.chanda.payroll.features.componenttype;

import com.istad.chanda.payroll.features.componenttype.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ComponentTypeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ComponentType toEntity(CreateComponentTypeRequest request);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void update(UpdateComponentTypeRequest request, @MappingTarget ComponentType entity);
    ComponentTypeResponse toResponse(ComponentType entity);
}
