package com.tesis.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "zone_filter")
@Data
@Builder
public class ZoneFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "zoneFilters", allowSetters = true)
    public Zone zone;

    @Column(name = "filter_inside")
    public Boolean filterInside;

    @ManyToOne
    @JsonIgnoreProperties(value = "zoneFilters", allowSetters = true)
    public Map map;

}
