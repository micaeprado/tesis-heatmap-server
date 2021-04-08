package com.tesis.demo.controller;

import com.tesis.demo.model.dto.HeatmapDto;
import com.tesis.demo.model.dto.MapDto;
import com.tesis.demo.model.dto.WeightedLocDto;
import com.tesis.demo.model.dto.ZoneDto;
import com.tesis.demo.service.AnalysisService;
import com.tesis.demo.service.HeatmapService;
import com.tesis.demo.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/heatmap")
@RequiredArgsConstructor
public class HeatmapController {

    protected final HeatmapService heatmapService;
    protected final ZoneService zoneService;
    protected final AnalysisService analysisService;

    @GetMapping("/{id}")
    public HeatmapDto getMaps(@PathVariable String id){
        return heatmapService.getHeatmap(id);
    }

    @GetMapping("/page/{page}")
    public Page<HeatmapDto> getMaps(@PathVariable Integer page){
        return heatmapService.getHeatmapPerPage(PageRequest.of(page, 5));
    }

    @PostMapping()
    public List<WeightedLocDto> createMap(@RequestBody MapDto map) {
        return analysisService.createMap(map);
    }

    @PostMapping("/zones")
    public List<ZoneDto> getHeatmapZones(@RequestBody List<String> zones) {
        return zoneService.getZones(zones);
    }

}
