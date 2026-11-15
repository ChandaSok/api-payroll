package com.istad.chanda.payroll.features.exchangerate;

import com.istad.chanda.payroll.features.exchangerate.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ExchangeRateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ExchangeRate toEntity(CreateExchangeRateRequest request);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void update(UpdateExchangeRateRequest request, @MappingTarget ExchangeRate entity);
    ExchangeRateResponse toResponse(ExchangeRate entity);
}
