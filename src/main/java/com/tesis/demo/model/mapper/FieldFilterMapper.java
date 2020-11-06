package com.tesis.demo.model.mapper;

import com.tesis.demo.model.FieldFilter;
import com.tesis.demo.model.dto.FieldFilterDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FieldFilterMapper {
    public static FieldFilterDto toDto(FieldFilter fieldFilter) {
        if (fieldFilter == null) {
            return null;
        }
        return FieldFilterDto.builder()
                .id(fieldFilter.getId())
                .field(fieldFilter.getField())
                .filterName(fieldFilter.getFilterName())
                .valuesToFilter(fieldFilter.getValuesToFilter())
                .layer(LayerMapper.toDto(fieldFilter.getLayer()))
                .build();

    }


    public static FieldFilter toEntity(FieldFilterDto fieldFilterDto) {
        if (fieldFilterDto == null) {
            return null;
        }
        FieldFilter fieldFilter = FieldFilter.builder()
                .id(fieldFilterDto.getId())
                .field(fieldFilterDto.getField())
                .filterName(fieldFilterDto.getFilterName())
                .layer(LayerMapper.toEntity(fieldFilterDto.getLayer()))
                .build();
        fieldFilter.setValuesToFilter(fieldFilterDto.getValuesToFilter());
        return fieldFilter;
    }

    public static List<FieldFilterDto> fieldFiltersToFieldFilterDTOs(List<FieldFilter> fieldFilters){
        return fieldFilters.stream()
                .filter(Objects::nonNull)
                .map(FieldFilterMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<FieldFilter> fieldFiltersDTOsToFieldFilters(List<FieldFilterDto> fieldFilterDtos){
        return fieldFilterDtos.stream()
                .filter(Objects::nonNull)
                .map(FieldFilterMapper::toEntity)
                .collect(Collectors.toList());
    }
}
