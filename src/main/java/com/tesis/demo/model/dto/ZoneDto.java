package com.tesis.demo.model.dto;

import com.tesis.demo.model.Zone;

import java.util.List;

public class ZoneDto {
    protected Long id;
    protected String name;
    protected List<PointZoneDto> points;

    public ZoneDto() {
    }

    public Long getId() {
        return id;
    }

    public ZoneDto id(Long id) {
        this.id = id;
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ZoneDto name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PointZoneDto> getPoints() {
        return points;
    }

    public ZoneDto points(List<PointZoneDto> points) {
        this.points = points;
        return this;
    }

    public void setPoints(List<PointZoneDto> points) {
        this.points = points;
    }

}
