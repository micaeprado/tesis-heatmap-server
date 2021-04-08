package com.tesis.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tesis.demo.model.enumeration.FilterType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "field_filter")
@Data
@Builder
public class FieldFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "filter_name")
    protected FilterType filterName;

    @Column(name = "field")
    protected String field;

    @Column(name = "values_to_filter")
    protected String valuesToFilter;

    @ManyToOne
    @JsonIgnoreProperties(value = "fieldFilters", allowSetters = true)
    protected Map map;

    public List<String> getValuesToFilter() {
        return Arrays.asList(valuesToFilter.split(","));
    }

    public void setValuesToFilter(List<String> list) {
        valuesToFilter = String.join(",", list);
    }

}
