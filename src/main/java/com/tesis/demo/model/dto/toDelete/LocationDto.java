package com.tesis.demo.model.dto.toDelete;

import java.util.List;

public class LocationDto {
    protected List<PointDto> points;

    public LocationDto() {
    }

    public List<PointDto> getPoints() {
        return points;
    }

    public void setPoints(List<PointDto> points) {
        this.points = points;
    }

}
