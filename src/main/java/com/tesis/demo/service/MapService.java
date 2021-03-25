package com.tesis.demo.service;

import com.tesis.demo.model.Map;
import com.tesis.demo.model.dto.MapDto;
import com.tesis.demo.model.mapper.MapMapper;
import com.tesis.demo.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MapService {

    private final MapRepository mapRepository;

    public Map save(MapDto map) {
        return mapRepository.save(MapMapper.toEntity(map));
    }

    public List<MapDto> getAllMaps() {
        return MapMapper.mapsToMapsDtoList(mapRepository.findAll());
    }

    public Page<MapDto> getMapsPerPage(Pageable pageable) {
        return mapRepository.findAll(pageable)
                .map(MapMapper::toDto);
    }
}
