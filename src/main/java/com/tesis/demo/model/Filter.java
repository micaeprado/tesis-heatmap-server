package com.tesis.demo.model;

import com.tesis.demo.model.enumeration.FilterType;
import com.tesis.demo.model.enumeration.ObjectType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Document(collection = "filter")
@Data
@Builder
public class Filter {

    @Id
    protected String id;

    @Field("type")
    @Enumerated(EnumType.STRING)
    protected FilterType type;

    @Field("object_types")
    @Enumerated(EnumType.STRING)
    protected List<ObjectType> objectTypes;
}
