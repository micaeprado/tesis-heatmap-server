package com.tesis.demo.repository;

import com.tesis.demo.model.FileData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FileDataRepository extends MongoRepository<FileData, String> {

    Optional<FileData> findById(String id);

    @Query("{ 'file_name' : ?0}")
    List<FileData> findByFileName(String fileName);

}
