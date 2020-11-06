package com.tesis.demo.service;

import com.tesis.demo.model.Layer;
import com.tesis.demo.repository.LayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LayerService {

    private final LayerRepository layerRepository;

    public Layer save(Layer layer) {
        return layerRepository.save(layer);
    }
}
