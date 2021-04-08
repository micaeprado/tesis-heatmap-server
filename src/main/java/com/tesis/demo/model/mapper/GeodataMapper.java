package com.tesis.demo.model.mapper;

import com.tesis.demo.model.Geodata;
import com.tesis.demo.model.dto.GeodataDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeodataMapper {

    public static GeodataDto toDto(Geodata geodata) {
        if (geodata == null) {
            return null;
        }
        return  GeodataDto.builder()
                .id(geodata.getId())
                .fileName(geodata.getFileName())
                .lat(geodata.getLat())
                .lng(geodata.getLng())
                .fields(geodata.getFields())
                .build();
    }

    public static Geodata toEntity(GeodataDto geodata) {
        if (geodata == null) {
            return null;
        }
        return  Geodata.builder()
                .id(geodata.getId())
                .fileName(geodata.getFileName())
                .lat(geodata.getLat())
                .lng(geodata.getLng())
                .fields(geodata.getFields())
                .build();
    }

    public static List<Geodata> toEntityList(List<GeodataDto> geodataDtos){
        if (geodataDtos == null){
            return null;
        }
        return geodataDtos.stream().map(GeodataMapper::toEntity).collect(Collectors.toList());
    }

    public static List<GeodataDto> toDTOList(List<Geodata> geodata){
        if (geodata == null){
            return null;
        }
        return geodata.stream().map(GeodataMapper::toDto).collect(Collectors.toList());
    }
}
