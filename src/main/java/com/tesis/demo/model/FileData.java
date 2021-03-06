package com.tesis.demo.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "file_data")
@Data
@Builder
public class FileData {

    @Id
    protected String id;

    @Field("file_name")
    protected String fileName;

    @Field("creation_date")
    @CreationTimestamp
    protected LocalDateTime creationDate;

    @Field("header")
    protected List<Header> header;

}
