package com.tesis.demo.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ZoneDto {

    protected Long id;

    protected String name;

    protected String description;

    protected LocalDateTime creationDate;

    protected List<PointZoneDto> points;

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

    public String getDescription() {
        return description;
    }

    public ZoneDto description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public ZoneDto creationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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
