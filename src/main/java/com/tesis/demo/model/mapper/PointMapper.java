package com.tesis.demo.model.mapper;

import com.tesis.demo.model.Point;
import com.tesis.demo.model.dto.PointDto;
import org.springframework.stereotype.Service;

@Service
public class PointMapper {

    public static PointDto toDto(Point point) {
        if (point == null) {
            return null;
        }
        return PointDto
                .builder()
                .lng(point.getLng())
                .lat(point.getLat())
                .build();
    }

    public static Point toEntity(PointDto pointDto) {
        if (pointDto == null) {
            return null;
        }
        return Point
                .builder()
                .lng(pointDto.getLng())
                .lat(pointDto.getLat())
                .build();
    }
}
