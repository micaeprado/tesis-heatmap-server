package com.tesis.demo.service;

import com.tesis.demo.model.Geodata;
import com.tesis.demo.repository.GeodataDAO;
import com.tesis.demo.repository.GeodataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeodataService {

    private final GeodataRepository geodataRepository;
    private final GeodataDAO geodataDAO;

    public Geodata save(Geodata geodata) {
        return geodataRepository.save(geodata);
    }

    public Geodata saveGeodata(String originalFilename, double lat, double lng, Map<String, String> fields) {
        Geodata geodata = Geodata.builder()
                .fileName(originalFilename)
                .lat(lat)
                .lng(lng)
                .fields(fields)
                .build();
        return save(geodata);
    }

    public List<Geodata> getGeodataByFileName(String fileName) {
        return geodataRepository.findByFileName(fileName);
    }

    public List<String> getFiltersByHeader(String name, String header) {
        return geodataDAO.findDistinctByFileNameAndField(name, header);
    }

    public List<Geodata> getFilteredElements(String fileName, String field, String fieldValue) {
        return geodataRepository.findByFileNameAndFieldAndFieldValue(fileName, field, fieldValue);
    }


}
