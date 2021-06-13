package com.tesis.demo.service;

import com.tesis.demo.model.Geodata;
import com.tesis.demo.model.dto.FieldFilterDto;
import com.tesis.demo.model.enumeration.FilterType;
import com.tesis.demo.model.enumeration.ObjectType;
import com.tesis.demo.repository.GeodataDAO;
import com.tesis.demo.repository.GeodataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeodataService {

    private final GeodataRepository geodataRepository;
    private final GeodataDAO geodataDAO;
    private final FileDataService fileDataService;

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

    public List<Geodata> getFilteredElements(String fileName, List<FieldFilterDto> fieldFilters) {
        List<Geodata> geodata = geodataRepository.findByFileName(fileName);
        if(fieldFilters != null)  {
            for (FieldFilterDto fieldFilter : fieldFilters) {
                geodata = filterGeodata(fieldFilter, geodata);
            }
        }
        return geodata;
    }

    private List<Geodata> filterGeodata(FieldFilterDto fieldFilter, List<Geodata> geodata) {
        List<Geodata> newGeodata = new ArrayList<>();
        if (fieldFilter == null) {
            return geodata;
        }
        for (Geodata data: geodata) {
            String fieldData = data.getFields().get(fieldFilter.getField());
            if(fieldData != null) {
                ObjectType fieldObjectType = fileDataService.getFieldObjectType(fieldFilter.getField(), fieldFilter.getMap().getFileName());
                if (FilterType.EQUAL.equals(fieldFilter.getFilterName())) {
                    List<String> values = new ArrayList<>(new HashSet<>(fieldFilter.getValuesToFilter()));
                    for (String value : values) {
                        if (fieldData.equals(value)) {
                            newGeodata.add(data);
                        }
                    }
                } else if (FilterType.DIFFERENT.equals(fieldFilter.getFilterName())) {
                    List<String> values = new ArrayList<>(new HashSet<>(fieldFilter.getValuesToFilter()));
                    for (String value : values) {
                        if (!fieldData.equals(value)) {
                            newGeodata.add(data);
                        }
                    }
                } else if (FilterType.GREATER.equals(fieldFilter.getFilterName())) {
                    String stringValue = fieldFilter.getValuesToFilter().get(0);
                    if (ObjectType.NUMBER.equals(fieldObjectType)) {
                        BigDecimal numberValue = new BigDecimal(stringValue);
                        BigDecimal numberFieldData = new BigDecimal(fieldData);
                        if (numberFieldData.compareTo(numberValue) > 0) {
                            newGeodata.add(data);
                        }
                    }
                } else if (FilterType.GREATER_EQUAL.equals(fieldFilter.getFilterName())) {
                    String stringValue = fieldFilter.getValuesToFilter().get(0);
                    if (ObjectType.NUMBER.equals(fieldObjectType)) {
                        BigDecimal numberValue = new BigDecimal(stringValue);
                        BigDecimal numberFieldData = new BigDecimal(fieldData);
                        if (numberFieldData.compareTo(numberValue) == 0 || numberFieldData.compareTo(numberValue) > 0) {
                            newGeodata.add(data);
                        }
                    }

                } else if (FilterType.LESS.equals(fieldFilter.getFilterName())) {
                    String stringValue = fieldFilter.getValuesToFilter().get(0);
                    if (ObjectType.NUMBER.equals(fieldObjectType)) {
                        BigDecimal numberValue = new BigDecimal(stringValue);
                        BigDecimal numberFieldData = new BigDecimal(fieldData);
                        if (numberFieldData.compareTo(numberValue) < 0) {
                            newGeodata.add(data);
                        }
                    }
                } else {
                    String stringValue = fieldFilter.getValuesToFilter().get(0);
                    if (ObjectType.NUMBER.equals(fieldObjectType)) {
                        BigDecimal numberValue = new BigDecimal(stringValue);
                        BigDecimal numberFieldData = new BigDecimal(fieldData);
                        if (numberFieldData.compareTo(numberValue) == 0 || numberFieldData.compareTo(numberValue) < 0) {
                            newGeodata.add(data);
                        }
                    }
                }
            }
        }
        return newGeodata;
    }




}
