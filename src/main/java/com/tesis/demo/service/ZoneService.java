package com.tesis.demo.service;

import com.tesis.demo.model.Zone;
import com.tesis.demo.model.dto.ZoneDto;
import com.tesis.demo.model.mapper.ZoneMapper;
import com.tesis.demo.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;

    public List<ZoneDto> getZones(List<String> zones) {
        List<Zone> allZones = zoneRepository.findAll();
        return allZones.stream()
                .filter(zone -> zones.contains(zone.getId().toString()))
                .map(ZoneMapper::toDto)
                .collect(Collectors.toList());
    }
}
