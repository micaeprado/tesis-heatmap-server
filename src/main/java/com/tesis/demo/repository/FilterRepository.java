package com.tesis.demo.repository;

import com.tesis.demo.model.Filter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FilterRepository extends MongoRepository<Filter, String> {

}
