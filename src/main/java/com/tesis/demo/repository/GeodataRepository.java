package com.tesis.demo.repository;

import com.tesis.demo.model.Geodata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GeodataRepository extends MongoRepository<Geodata, String> {

    Geodata findById(Long id);

    Geodata findByLatAndLng(String lat, String lng);

    List<Geodata> findByFileName(String fileName);

    @Query("{ 'file_name' : ?0, 'fields.?1' : ?2}")
    List<Geodata> findByFileNameAndFieldAndFieldValue(String fileName, String field, String fieldValue);

}
