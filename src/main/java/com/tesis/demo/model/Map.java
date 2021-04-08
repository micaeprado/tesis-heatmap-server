package com.tesis.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "map")
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "name")
    protected String name;

    @Column(name = "file_name")
    protected String fileName;

    @Column(name = "function_name")
    protected String functionName;

    @Column(name = "field_to_calculate")
    protected String fieldToCalculate;

    @Column(name = "creation_date")
    @CreationTimestamp
    protected LocalDateTime creationDate;

    @OneToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = "map", allowSetters = true)
    protected List<ZoneFilter> zoneFilters;

    @OneToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = "map", allowSetters = true)
    protected List<FieldFilter> fieldFilters;

}
