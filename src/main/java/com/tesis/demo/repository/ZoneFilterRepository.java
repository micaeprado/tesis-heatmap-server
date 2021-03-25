package com.tesis.demo.repository;

import com.tesis.demo.model.ZoneFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneFilterRepository extends JpaRepository<ZoneFilter, Long> {
}

