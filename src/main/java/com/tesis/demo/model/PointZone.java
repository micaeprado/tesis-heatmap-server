package com.tesis.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "point_zone")
public class PointZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected double lat;

    protected double lng;

    @ManyToOne
    @JsonIgnoreProperties(value = "points", allowSetters = true)
    private Zone zone;

    public PointZone() {
    }

    public Long getId() {
        return id;
    }

    public PointZone id(Long id) {
        this.id = id;
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PointZone lat(double lat) {
        this.lat = lat;
        return this;
    }
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public PointZone lng(double lng) {
        this.lng = lng;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public PointZone zone(Zone zone) {
        this.zone = zone;
        return this;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
