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
    protected Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "zoneFilters", allowSetters = true)
    protected Zone zone;

    @Column(name = "filter_add")
    protected Boolean filterAdd;

    @ManyToOne
    @JsonIgnoreProperties(value = "zoneFilters", allowSetters = true)
    protected Map map;

}
