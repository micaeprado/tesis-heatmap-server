package com.tesis.demo.model;

import com.tesis.demo.model.enumeration.ObjectType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
public class Header {

    @Field("header")
    protected String header;

    @Field("object_type")
    protected ObjectType objectType;

}
