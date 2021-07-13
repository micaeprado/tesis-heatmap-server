package com.tesis.demo.service;

import com.tesis.demo.model.FieldFilter;
import com.tesis.demo.model.Map;
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

    public List<FieldFilterDto> saveAll(List<FieldFilter> fieldFilters, Map map) {
        if (fieldFilters == null) {
            return new ArrayList<>();
        }

        List<FieldFilterDto> fieldFilterDTOs = new ArrayList<>();
        fieldFilters.forEach(fieldFilter -> {
            fieldFilter.setMap(map);
            fieldFilterDTOs.add(save(fieldFilter));
        });

        return fieldFilterDTOs;
    }

    public FieldFilterDto save(FieldFilter fieldFilter) {
        return FieldFilterMapper.toDto(fieldFilterRepository.save(fieldFilter));
    }
}
