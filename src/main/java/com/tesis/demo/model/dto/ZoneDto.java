package com.tesis.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDto {
    protected Long id;
    protected String name;
    protected String description;
    protected LocalDateTime creationDate;
    protected List<PointZoneDto> points;

}
