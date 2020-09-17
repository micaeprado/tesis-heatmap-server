package com.tesis.demo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Document(collection = "geo_data")
@Data
@Builder
public class Geodata {

    @Id
    public String id;

    @Field("file_name")
    public String fileName;

    @Field("lat")
    public double lat;

    @Field("lng")
    public double lng;

    @Field("fields")
    public Map<String, String> fields;

}
