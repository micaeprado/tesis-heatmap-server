package com.tesis.demo.model.dto;

public class PointZoneDto {

    protected Long id;
    protected double lat;
    protected double lng;

    public PointZoneDto() {
    }

    public Long getId() {
        return id;
    }

    public PointZoneDto id(Long id) {
        this.id = id;
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public PointZoneDto lat(double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public PointZoneDto lng(double lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
