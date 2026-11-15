package com.istad.chanda.payroll.features.timeattendance;

import com.istad.chanda.payroll.features.timeattendance.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TimeAttendanceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "employee", ignore = true)
    TimeAttendance toEntity(CreateTimeAttendanceRequest request);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "employee", ignore = true)
    void update(UpdateTimeAttendanceRequest request, @MappingTarget TimeAttendance entity);
    TimeAttendanceResponse toResponse(TimeAttendance entity);
}
