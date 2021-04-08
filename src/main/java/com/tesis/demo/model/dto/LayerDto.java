package com.tesis.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class LayerDto {

    public Long id;

    public String name;

    @JsonProperty("fileName")
    public String fileName;

    @JsonProperty("functionName")
    public String functionName;

    @JsonProperty("fieldToCalculate")
    public String fieldToCalculate;

    @JsonProperty("zone")
    public ZoneDto zone;

    public LocalDateTime creationDate;

    @JsonProperty("fieldFilters")
    public List<FieldFilterDto> fieldFilters;

}
