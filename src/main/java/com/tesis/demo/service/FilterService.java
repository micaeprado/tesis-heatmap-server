package com.tesis.demo.service;

import com.tesis.demo.model.Filter;
import com.tesis.demo.repository.FilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterService {

    private final FilterRepository filterRepository;

    public List<Filter> getAllFilters() {
        return filterRepository.findAll();
    }
}
