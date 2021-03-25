package com.tesis.demo.model.dto;

import com.tesis.demo.model.enumeration.FilterType;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class FieldFilterDto {
    
    public Long id;
    
    public FilterType filterName;

    public String field;

    public List<String> valuesToFilter;

    public MapDto map;

}
