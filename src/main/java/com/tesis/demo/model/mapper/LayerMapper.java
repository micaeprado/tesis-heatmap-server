package com.tesis.demo.model.mapper;

import com.tesis.demo.model.Layer;
import com.tesis.demo.model.dto.LayerDto;
import org.springframework.stereotype.Service;

@Service
public class LayerMapper {
    public static LayerDto toDto(Layer layer) {
        if (layer == null) {
            return null;
        }
        return LayerDto.builder()
                .id(layer.getId())
                .fileName(layer.getFileName())
                .functionName(layer.getFunctionName())
                .fieldToCalculate(layer.getFieldToCalculate())
               // .zone(ZoneMapper.toDto(layer.getZone()))
                .build();

    }


    public static Layer toEntity(LayerDto layerDto) {
        if (layerDto == null) {
            return null;
        }
        return Layer.builder()
                .id(layerDto.getId())
                .fileName(layerDto.getFileName())
                .functionName(layerDto.getFunctionName())
                .fieldToCalculate(layerDto.getFieldToCalculate())
                //.zone(ZoneMapper.toEntity(layerDto.getZone()))
                .build();

    }
}
