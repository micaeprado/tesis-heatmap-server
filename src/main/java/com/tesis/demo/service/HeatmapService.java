package com.tesis.demo.service;

import com.tesis.demo.model.Heatmap;
import com.tesis.demo.model.dto.HeatmapDto;
import com.tesis.demo.model.mapper.HeatmapMapper;
import com.tesis.demo.repository.HeatmapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;


@Service
@RequiredArgsConstructor
public class HeatmapService {

    private final HeatmapRepository heatmapRepository;

    public Heatmap save(Heatmap map) {
        return heatmapRepository.save(map);
    }

    public Page<HeatmapDto> getHeatmapPerPage(Pageable pageable) {
        return heatmapRepository.findAll(pageable)
                .map(HeatmapMapper::toDto);
    }

    public HeatmapDto getHeatmap(String id) {
        Heatmap heatmap = heatmapRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return HeatmapMapper.toDto(heatmap);
    }
}
