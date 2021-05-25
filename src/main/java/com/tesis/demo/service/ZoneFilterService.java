package com.tesis.demo.service;

import com.tesis.demo.model.Map;
import com.tesis.demo.model.ZoneFilter;
import com.tesis.demo.model.dto.ZoneFilterDto;
import com.tesis.demo.model.mapper.ZoneFilterMapper;
import com.tesis.demo.repository.ZoneFilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneFilterService {

    private final ZoneFilterRepository zoneFilterRepository;

    public List<ZoneFilterDto> saveAll(List<ZoneFilter> zoneFilters, Map map) {
        if(zoneFilters == null) {
            return null;
        }

        List<ZoneFilterDto> zoneFilterDtos = new ArrayList<>();
        zoneFilters.forEach(zoneFilter -> {
                    zoneFilter.setMap(map);
                    zoneFilterDtos.add(save(zoneFilter));
                });

        return zoneFilterDtos;
    }

    public ZoneFilterDto save(ZoneFilter zoneFilter) {
        return ZoneFilterMapper.toDto(zoneFilterRepository.save(zoneFilter));
    }
}
