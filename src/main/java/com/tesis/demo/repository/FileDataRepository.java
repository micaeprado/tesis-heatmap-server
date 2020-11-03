package com.tesis.demo.repository;

import com.tesis.demo.model.FileData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FileDataRepository extends MongoRepository<FileData, String> {

    Optional<FileData> findById(String id);

}
