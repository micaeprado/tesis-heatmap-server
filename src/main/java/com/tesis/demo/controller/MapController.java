package com.tesis.demo.controller;

import com.tesis.demo.model.dto.MapDto;
import com.tesis.demo.service.AnalysisService;
import com.tesis.demo.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/map")
@RequiredArgsConstructor
public class MapController {

    protected final MapService mapService;
    protected final AnalysisService analysisService;

    @GetMapping()
    public List<MapDto> getAllMaps() {
        return mapService.getAllMaps();
    }

    @GetMapping("/page/{page}")
    public Page<MapDto> getMaps(@PathVariable Integer page){
        return mapService.getMapsPerPage(PageRequest.of(page, 5));
    }

}
