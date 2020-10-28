package com.tesis.demo.repository;

import com.tesis.demo.model.FieldFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldFilterRepository extends JpaRepository<FieldFilter, Long> {
}

