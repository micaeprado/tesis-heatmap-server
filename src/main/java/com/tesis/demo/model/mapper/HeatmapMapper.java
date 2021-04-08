package com.tesis.demo.model.mapper;

import com.tesis.demo.model.Heatmap;
import com.tesis.demo.model.dto.HeatmapDto;
import org.springframework.stereotype.Service;

@Service
public class HeatmapMapper {

    public static HeatmapDto toDto(Heatmap heatmap) {
        if (heatmap == null) {
            return null;
        }
        return  HeatmapDto.builder()
                .id(heatmap.getId())
                .name(heatmap.getName())
                .creationDate(heatmap.getCreationDate())
                .zonesId(heatmap.getZonesId())
                .weightedLocs(WeightedLocMapper.toDTOList(heatmap.getWeightedLocs()))
                .geodata(GeodataMapper.toDTOList(heatmap.getGeodata()))
                .build();
    }

    public static Heatmap toEntity(HeatmapDto heatmap) {
        if (heatmap == null) {
            return null;
        }
        return  Heatmap.builder()
                .id(heatmap.getId())
                .name(heatmap.getName())
                .creationDate(heatmap.getCreationDate())
                .zonesId(heatmap.getZonesId())
                .weightedLocs(WeightedLocMapper.toEntityList(heatmap.getWeightedLocs()))
                .geodata(GeodataMapper.toEntityList(heatmap.getGeodata()))
                .build();
    }

}
