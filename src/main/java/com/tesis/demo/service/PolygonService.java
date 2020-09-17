package com.tesis.demo.service;

import com.tesis.demo.model.PointZone;
import com.tesis.demo.model.Zone;
import com.tesis.demo.model.dto.PointZoneDto;
import com.tesis.demo.model.dto.ZoneDto;
import com.tesis.demo.repository.PointZoneRepository;
import com.tesis.demo.repository.ZoneRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PolygonService {

    protected final ZoneRepository zoneRepository;
    protected final PointZoneRepository pointZoneRepository;
    protected final ModelMapper modelMapper;

    public PolygonService(ZoneRepository zoneRepository, PointZoneRepository pointZoneRepository, ModelMapper modelMapper) {
        this.zoneRepository = zoneRepository;
        this.pointZoneRepository = pointZoneRepository;
        this.modelMapper = modelMapper;
    }

    public List<ZoneDto> getAllZones() {
         List<Zone> zones = zoneRepository.findAll();
         return zones.stream()
                 .map(this::mapperZoneToDTO)
                 .collect(Collectors.toList());
    }

    private List<PointZoneDto> getPointsDtoByZone(Zone zone) {
        List<PointZone> pointZones = pointZoneRepository.getAllByZone(zone);
        return pointZones.stream()
                .map(this::mapperPointZoneToDTO)
                .collect(Collectors.toList());
    }

    private PointZoneDto mapperPointZoneToDTO(PointZone pointZone) {
        return new PointZoneDto()
                .id(pointZone.getId())
                .lat(pointZone.getLat())
                .lng(pointZone.getLng());
    }

    private ZoneDto mapperZoneToDTO(Zone zone) {
        List<PointZoneDto> pointsDto = getPointsDtoByZone(zone);
        return new ZoneDto()
                .id(zone.getId())
                .name(zone.getName())
                .points(pointsDto);
    }

    public Zone getZoneById(Long id) {
        Optional<Zone> searchedZone = zoneRepository.findById(id);
        Zone zone = searchedZone.orElseThrow(NoSuchElementException::new);
        return zone;
    }

    public Zone createZone(ZoneDto zoneDto) {
        Zone zone = new Zone();

        Zone newZone = zoneRepository.save(zone);
        createPoints(zoneDto.getPoints(), newZone);
        return newZone;
    }

    private List<PointZone> createPoints(List<PointZoneDto> points, Zone zone) {
        List<PointZone> pointZones = new ArrayList<>();
        for (PointZoneDto pointDto: points) {
            PointZone point = new PointZone()
                    .lat(pointDto.getLat())
                    .lng(pointDto.getLng())
                    .zone(zone);
            PointZone newPoint = pointZoneRepository.save(point);
            pointZones.add(newPoint);
        }
        return pointZones;
    }
}
