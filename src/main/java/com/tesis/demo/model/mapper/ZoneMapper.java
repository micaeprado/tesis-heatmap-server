package com.tesis.demo.model.mapper;

import com.tesis.demo.model.Zone;
import com.tesis.demo.model.dto.ZoneDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZoneMapper {

    public static ZoneDto toDto(Zone zone) {
        if (zone == null) {
            return null;
        }
        return new ZoneDto()
                .id(zone.getId())
                .name(zone.getName())
                .description(zone.getDescription())
                .creationDate(zone.getCreationDate())
                .points(PointMapper.toDTOList(zone.getPoints()));
    }

    public static Zone toEntity(ZoneDto zone) {
        if (zone == null) {
            return null;
        }
        return new Zone()
                .id(zone.getId())
                .name(zone.getName())
                .description(zone.getDescription())
                .creationDate(zone.getCreationDate());
    }

    public static List<Zone> toEntityList(List<ZoneDto> zoneDtos){
        if (zoneDtos == null){
            return null;
        }
        return zoneDtos.stream().map(ZoneMapper::toEntity).collect(Collectors.toList());
    }

    public static List<ZoneDto> toDTOList(List<Zone> zones){
        if (zones == null){
            return null;
        }
        return zones.stream().map(ZoneMapper::toDto).collect(Collectors.toList());
    }
}
