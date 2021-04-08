package com.tesis.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointZoneDto {
    protected Long id;
    protected double lat;
    protected double lng;
    protected ZoneDto zone;
}
