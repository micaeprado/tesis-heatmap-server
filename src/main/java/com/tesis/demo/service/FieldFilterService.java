package com.tesis.demo.service;

import com.tesis.demo.model.FieldFilter;
import com.tesis.demo.model.Layer;
import com.tesis.demo.model.dto.FieldFilterDto;
import com.tesis.demo.model.mapper.FieldFilterMapper;
import com.tesis.demo.repository.FieldFilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldFilterService {

    private final FieldFilterRepository fieldFilterRepository;

    public List<FieldFilterDto> saveAll(List<FieldFilter> fieldFilters, Layer layer) {
        List<FieldFilterDto> fieldFilterDTOs = new ArrayList<>();
        for (FieldFilter fieldFilter:fieldFilters) {
            fieldFilter.setLayer(layer);
            fieldFilterDTOs.add(FieldFilterMapper.toDto(fieldFilterRepository.save(fieldFilter)));
        }
        return fieldFilterDTOs;
    }

    public FieldFilter save(FieldFilter fieldFilter) {
        return fieldFilterRepository.save(fieldFilter);
    }
}
