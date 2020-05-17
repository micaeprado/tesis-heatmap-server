package com.tesis.demo.model.dto;

import java.util.Date;

public class IncidentDto {

    private Long id;

    private String type;

    private Date startTime;

    private Date endTime;

    private Long seconds;

    private Long promedio;

    //@Convert(converter = LocationConverterJson.class) //convierte json a Location y al reves
    private LocationDto location;

    public IncidentDto() {
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

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public Long getSeconds() {
        return seconds;
    }

    public void setSeconds(Long seconds) {
        this.seconds = seconds;
    }

    public Long getPromedio() {
        return promedio;
    }

    public void setPromedio(Long promedio) {
        this.promedio = promedio;
    }
}
