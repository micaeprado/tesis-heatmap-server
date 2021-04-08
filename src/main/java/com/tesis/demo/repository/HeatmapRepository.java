package com.tesis.demo.repository;

import com.tesis.demo.model.Heatmap;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HeatmapRepository extends MongoRepository<Heatmap, String> {
}

