package com.tesis.demo.controller;

import com.tesis.demo.model.Zone;
import com.tesis.demo.model.dto.ZoneDto;
import com.tesis.demo.service.PolygonService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/polygon")
public class PolygonController {

    protected final PolygonService polygonService;
    protected final ModelMapper modelMapper;

    private final Logger log = LoggerFactory.getLogger(PolygonController.class);

    public PolygonController(PolygonService polygonService, ModelMapper modelMapper) {
        this.polygonService = polygonService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<ZoneDto> getZones(){
        return polygonService.getAllZones();
    }

    @GetMapping("/page/{page}")
    public Page<ZoneDto> getZones(@PathVariable Integer page){
        return polygonService.getAllZones(PageRequest.of(page, 5));
    }

    @PostMapping
    public ResponseEntity<Zone> createZone(@RequestBody ZoneDto zoneDto) {
        log.debug("REST request to save Zone : {}", zoneDto);
        Zone newZone = polygonService.createZone(zoneDto);
        return ResponseEntity.ok(newZone);
    }

    @GetMapping("/{id}")
    public ZoneDto getZone(@PathVariable Long id){
        return polygonService.getZoneById(id);
    }
}
