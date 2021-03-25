package com.tesis.demo.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class MapDto {

    public Long id;

    public String name;

    public String fileName;

    public String functionName;

    public String fieldToCalculate;

    public LocalDateTime creationDate;

    public List<ZoneFilterDto> zoneFilters;

    public List<FieldFilterDto> fieldFilters;
}
