package com.tesis.demo.model.mapper;

import com.tesis.demo.model.PointZone;
import com.tesis.demo.model.dto.PointZoneDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointZoneMapper {

    public static PointZoneDto toDto(PointZone point) {
        if (point == null) {
            return null;
        }
        return PointZoneDto
                .builder()
                .id(point.getId())
                .lng(point.getLng())
                .lat(point.getLat())
                .build();
    }

    public static PointZone toEntity(PointZoneDto pointDto) {
        if (pointDto == null) {
            return null;
        }
        return PointZone
                .builder()
                .id(pointDto.getId())
                .lng(pointDto.getLng())
                .lat(pointDto.getLat())
                .build();
    }


    public static List<PointZoneDto> toDTOList(List<PointZone> points){
        if (points == null){
            return null;
        }
        return points.stream().map(PointZoneMapper::toDto).collect(Collectors.toList());
    }
}
