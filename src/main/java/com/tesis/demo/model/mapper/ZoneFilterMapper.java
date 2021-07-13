package com.tesis.demo.model.mapper;

import com.tesis.demo.model.ZoneFilter;
import com.tesis.demo.model.dto.ZoneFilterDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ZoneFilterMapper {
    public static ZoneFilterDto toDto(ZoneFilter zoneFilter) {
        if (zoneFilter == null) {
            return null;
        }
        return ZoneFilterDto.builder()
                .id(zoneFilter.getId())
                .zone(ZoneMapper.toDto(zoneFilter.getZone()))
                .filterAdd(zoneFilter.getFilterAdd())
                .map(MapMapper.toDto(zoneFilter.getMap()))
                .build();

    }


    public static ZoneFilter toEntity(ZoneFilterDto zoneFilterDto) {
        if (zoneFilterDto == null) {
            return null;
        }
        return ZoneFilter.builder()
                .id(zoneFilterDto.getId())
                .zone(ZoneMapper.toEntity(zoneFilterDto.getZone()))
                .filterAdd(zoneFilterDto.getFilterAdd())
                .map(MapMapper.toEntity(zoneFilterDto.getMap()))
                .build();
    }

    public static List<ZoneFilterDto> zoneFiltersToListDTOs(List<ZoneFilter> zoneFilters){
        if(zoneFilters == null) {
            return null;
        }
        return zoneFilters.stream()
                .filter(Objects::nonNull)
                .map(ZoneFilterMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<ZoneFilter> zoneFiltersDTOsToList(List<ZoneFilterDto> zoneFilterDtos){
        if(zoneFilterDtos == null) {
            return null;
        }
        return zoneFilterDtos.stream()
                .filter(Objects::nonNull)
                .map(ZoneFilterMapper::toEntity)
                .collect(Collectors.toList());
    }
}
