package com.tesis.demo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "file_data")
@Data
@Builder
public class FileData {

    @Id
    public String id;

    @Field("file_name")
    public String fileName;

    @Field("header")
    public List<String> header;

}
