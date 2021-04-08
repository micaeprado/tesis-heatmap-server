package com.tesis.demo.service;

import com.tesis.demo.model.PointZone;
import com.tesis.demo.model.Zone;
import com.tesis.demo.model.dto.PointZoneDto;
import com.tesis.demo.model.dto.ZoneDto;
import com.tesis.demo.model.dto.toDelete.PointDto;
import com.tesis.demo.model.mapper.PointZoneMapper;
import com.tesis.demo.model.mapper.ZoneMapper;
import com.tesis.demo.repository.PointZoneRepository;
import com.tesis.demo.repository.ZoneRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
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
         return ZoneMapper.toDTOList(zoneRepository.findAll());
    }

    public Page<ZoneDto> getAllZones(Pageable pageable) {
        return zoneRepository.findAll(pageable)
                .map(ZoneMapper::toDto);
    }

    public List<PointZoneDto> getPointsDtoByZone(ZoneDto zone) {
        return PointZoneMapper.toDTOList(pointZoneRepository.getAllByZone(ZoneMapper.toEntity(zone)));
    }

    public ZoneDto getZoneById(Long id) {
        Zone zone = zoneRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return ZoneMapper.toDto(zone);
    }

    public ZoneDto save(Zone zone) {
        return ZoneMapper.toDto(zoneRepository.save(zone));
    }

    public PointZoneDto savePoint(PointZone pointZone) {
        return PointZoneMapper.toDto(pointZoneRepository.save(pointZone));
    }

    public ZoneDto createZone(ZoneDto zoneDto) {
        ZoneDto newZone = save(ZoneMapper.toEntity(zoneDto));
        createPoints(zoneDto.getPoints(), newZone);
        return newZone;
    }

    private List<PointZone> createPoints(List<PointZoneDto> points, ZoneDto zone) {
        List<PointZone> pointZones = new ArrayList<>();
        for (PointZoneDto pointDto: points) {
            PointZone pointZone = PointZoneMapper.toEntity(pointDto);
            pointZone.setZone(ZoneMapper.toEntity(zone));
            PointZone newPoint = pointZoneRepository.save(pointZone);
            pointZones.add(newPoint);
        }
        return pointZones;
    }

    public boolean isPointInsideZone(PointDto point, ZoneDto zone) {
        List<PointZoneDto> points = getPointsDtoByZone(zone);
        int i, j;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++){
            if (((points.get(i).getLng() > point.getLng()) != (points.get(j).getLng() > point.getLng()))
                    && (point.getLat() < (points.get(j).getLat() - points.get(i).getLat()) *
                    (point.getLng() - points.get(i).getLng()) / (points.get(j).getLng() - points.get(i).getLng()) + points.get(i).getLat())) {
                return true;
            }
        }
        return false;
    }
}
