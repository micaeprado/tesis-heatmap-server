package com.tesis.demo.model.mapper;

import com.tesis.demo.model.Map;
import com.tesis.demo.model.dto.MapDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MapMapper {
    public static MapDto toDto(Map map) {
        if (map == null) {
            return null;
        }
        return MapDto.builder()
                .id(map.getId())
                .name(map.getName())
                .fileName(map.getFileName())
                .creationDate(map.getCreationDate())
                .functionName(map.getFunctionName())
                .fieldToCalculate(map.getFieldToCalculate())
                .build();
    }


    public static Map toEntity(MapDto map) {
        if (map == null) {
            return null;
        }
        return Map.builder()
                .id(map.getId())
                .name(map.getName())
                .fileName(map.getFileName())
                .creationDate(map.getCreationDate())
                .functionName(map.getFunctionName())
                .fieldToCalculate(map.getFieldToCalculate())
                .build();
    }

    public static List<MapDto> mapsToMapsDtoList(List<Map> maps){
        return maps.stream()
                .filter(Objects::nonNull)
                .map(MapMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Map> mapsDtoToMapsList(List<MapDto> mapDtos){
        return mapDtos.stream()
                .filter(Objects::nonNull)
                .map(MapMapper::toEntity)
                .collect(Collectors.toList());
    }
}
