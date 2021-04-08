package com.tesis.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@Document(collection = "heatmap")
public class HeatmapDto {

    @Id
    protected String id;

    @Field("name")
    protected String name;

    @Field("creation_date")
    @CreationTimestamp
    protected LocalDateTime creationDate;

    @Field("zones_id")
    protected List<String> zonesId;

    @Field("weighted_locs")
    protected List<WeightedLocDto> weightedLocs;

    @Field("geodata")
    protected List<GeodataDto> geodata;

}
