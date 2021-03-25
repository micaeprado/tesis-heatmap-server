package com.tesis.demo.service;

import com.tesis.demo.model.Header;
import com.tesis.demo.model.PointZone;
import com.tesis.demo.model.Zone;
import com.tesis.demo.model.dto.ZoneDto;
import com.tesis.demo.model.enumeration.ObjectType;
import com.tesis.demo.model.mapper.ZoneMapper;
import com.tesis.demo.service.exceptions.FileEmptyException;
import com.tesis.demo.service.exceptions.UploadFileException;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataLoaderService {

    private final GeodataService geodataService;
    private final FileDataService fileDataService;
    private final PolygonService polygonService;

    public void uploadFile(MultipartFile file, String latitude, String longitude) {
        List<String[]> csv = readCSV(file);
        int latPos = 0;
        int lngPos = 0;

        String[] header = csv.get(0);
        List<String> headers = new ArrayList<>();
        List<Header> fieldTypes = new ArrayList<>();

        for (int i=0; i<header.length; i++) {
            if (header[i].equals(latitude)){
                latPos = i;
                fieldTypes.add(null);
            } else if (header[i].equals(longitude)){
                lngPos = i;
                fieldTypes.add(null);
            } else {
                headers.add(header[i]);
                fieldTypes.add(Header.builder()
                                .header(header[i])
                                .objectType(null)
                                .build());
            }
        }

        for (int i=1; i<csv.size(); i++) {
            Map<String, String> fields = new HashMap<>();
            for (int j=0; j< header.length; j++) {
                if (j != latPos && j != lngPos) {
                    if (csv.get(i)[j] != null && !csv.get(i)[j].isEmpty()) {
                        fields.put(header[j], csv.get(i)[j]);
                    }
                    fieldTypes.get(j).setObjectType(getFieldType(fieldTypes.get(j).getObjectType(), csv.get(i)[j]));
                }
            }
            geodataService.saveGeodata(file.getOriginalFilename(), Double.parseDouble(csv.get(i)[latPos]),
                    Double.parseDouble(csv.get(i)[lngPos]), fields);
        }
        fileDataService.saveFileData(file.getOriginalFilename(), fieldTypes.stream().filter(Objects::nonNull).collect(Collectors.toList()));
    }

    private ObjectType getFieldType(ObjectType currentFieldType, String fieldValue) {

        if (ObjectType.STRING.equals(currentFieldType) ||
                (currentFieldType != null && !getFieldType(fieldValue).equals(currentFieldType))) {
            return ObjectType.STRING;
        }

        return getFieldType(fieldValue);
    }

    private ObjectType getFieldType(String s) {
        if(s != null && !s.isEmpty()) {
            if (GenericValidator.isInt(s)
                    || GenericValidator.isLong(s)
                    || GenericValidator.isFloat(s)
                    || GenericValidator.isFloat(s)) {
                return ObjectType.NUMBER;
            } else if (GenericValidator.isDate(s, "yyyy-MM-dd", true)
                    || GenericValidator.isDate(s, "dd-MM-yyyy", true)) {
                return ObjectType.DATE;
            }
        }
        return ObjectType.STRING;
    }

    public String[] readFileAndGetHeader(MultipartFile file) {
        if (!file.isEmpty()){
            List<String[]> csv = readCSV(file);
            return csv.get(0);
        }
        throw new FileEmptyException("The file is empty");
    }

    private List<String[]> readCSV(MultipartFile file) {
        try {
            CsvParserSettings settings = new CsvParserSettings();
            settings.detectFormatAutomatically();

            CsvParser parser = new CsvParser(settings);
            return parser.parseAll(new InputStreamReader(file.getInputStream()));
        } catch (Exception e) {
            throw new UploadFileException("There was an error loading the file", e);
        }
    }

    public void uploadZoneFile(MultipartFile file, String latitude, String longitude, String name, String description) {
        List<String[]> csv = readCSV(file);
        String[] header = csv.get(0);

        ZoneDto zone = polygonService.save(new Zone().name(name).description(description));

        for (int i=1; i<csv.size(); i++) {
            for (int j=0; j< header.length; j++) {
                PointZone pointZone = new PointZone();
                if (latitude.equals(header[j])) {
                   pointZone.setLat(Double.parseDouble(csv.get(i)[j]));
                }  else if (longitude.equals(header[j])) {
                    pointZone.setLng(Double.parseDouble(csv.get(i)[j]));
                }
                pointZone.setZone(ZoneMapper.toEntity(zone));
                polygonService.savePoint(pointZone);
            }
        }
    }
}
