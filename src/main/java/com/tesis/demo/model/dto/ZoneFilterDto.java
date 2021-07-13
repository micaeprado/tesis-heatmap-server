package com.tesis.demo.model.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ZoneFilterDto {
    
    private Long id;
    
    private ZoneDto zone;

    private Boolean filterAdd;

    private MapDto map;
}
