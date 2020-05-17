package com.tesis.demo.model;

import com.tesis.demo.model.converter.LocationConverterJson;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "incident")
public class Incident {

    @Id
    private Long id;

    private String type;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Convert(converter = LocationConverterJson.class) //convierte json a Location y al reves
    private Location location;

    public Incident() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date start_time) {
        this.startTime = start_time;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date end_time) {
        this.endTime = end_time;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
