package com.tesis.demo.repository;

import java.util.List;

public interface GeodataDAO {
    List<String> findDistinctByFileNameAndField(String fileName, String field);
}
