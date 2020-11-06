package com.tesis.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class LayerDto {

    public Long id;

    @JsonProperty("fileName")
    public String fileName;

    @JsonProperty("functionName")
    public String functionName;

    @JsonProperty("fieldToCalculate")
    public String fieldToCalculate;

    @JsonProperty("zone")
    public ZoneDto zone;

    @JsonProperty("fieldFilters")
    public List<FieldFilterDto> fieldFilters;

}
