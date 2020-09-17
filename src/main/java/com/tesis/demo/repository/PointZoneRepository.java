package com.tesis.demo.repository;

import com.tesis.demo.model.PointZone;
import com.tesis.demo.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointZoneRepository extends JpaRepository<PointZone, Long> {
    List<PointZone> getAllByZone(Zone zone);
}

