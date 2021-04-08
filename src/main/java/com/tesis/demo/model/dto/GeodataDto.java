package com.tesis.demo.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Document(collection = "geo_data")
@Data
@Builder
public class GeodataDto {

    @Id
    protected String id;

    @Field("file_name")
    protected String fileName;

    @Field("lat")
    protected double lat;

    @Field("lng")
    protected double lng;

    @Field("fields")
    protected Map<String, String> fields;
}
