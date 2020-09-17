package com.tesis.demo.repository.impl;

import com.mongodb.BasicDBObject;
import com.tesis.demo.repository.GeodataDAO;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class GeodataDAOImpl implements GeodataDAO {

    private final MongoTemplate mongoTemplate;

    public List<String> findDistinctByFileNameAndField(String fileName, String field){
        List<Document> result = mongoTemplate.getCollection("geo_data")
                .aggregate(Arrays.asList(
                        new BasicDBObject("$match", new BasicDBObject("file_name", fileName)),
                        new BasicDBObject("$group", new BasicDBObject("_id", String.format("$fields.%s", field)))
                ))
                .into(new ArrayList<>());
        return result.stream()
                .map(document -> String.valueOf(document.get("_id")))
                .collect(Collectors.toList());
    }

}
