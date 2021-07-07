package com.tesis.demo.service;

import com.tesis.demo.model.Header;
import com.tesis.demo.model.PointZone;
import com.tesis.demo.model.enumeration.ObjectType;
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

        for (int i=0; i<header.length; i++) { //recorre los headers
            if (header[i].equals(latitude)){
                latPos = i; // guarda posicion de latitud
                fieldTypes.add(null);
            } else if (header[i].equals(longitude)){
                lngPos = i; // guarda posicion de longitud
                fieldTypes.add(null);
            } else {
                headers.add(header[i]);  // guarda headers
                fieldTypes.add(Header.builder()
                                .header(header[i])
                                .objectType(null)
                                .build());
            }
        }

        for (int i=1; i<csv.size(); i++) { // recorre csv sin contar los headers
            Map<String, String> fields = new HashMap<>();
            for (int j=0; j< header.length; j++) {
                if (j != latPos && j != lngPos) {
                    if (csv.get(i)[j] != null && !csv.get(i)[j].isEmpty()) { // si tiene valor lo guarda, sino null
                        fields.put(header[j], csv.get(i)[j]);
                    } else {
                        fields.put(header[j], null);
                    }
                    fieldTypes.get(j).setObjectType(getFieldType(fieldTypes.get(j).getObjectType(), csv.get(i)[j]));
                }
            }
            if (csv.get(i)[latPos] != null && csv.get(i)[lngPos] != null) { // se asegura que tenga coordenadas
                geodataService.saveGeodata(file.getOriginalFilename(), Double.parseDouble(csv.get(i)[latPos]),
                        Double.parseDouble(csv.get(i)[lngPos]), fields);
            }
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

    public List<PointZone> readFileAndGetPoints(MultipartFile file, String latitude, String longitude) {
        if (!file.isEmpty()){
            List<String[]> csv = readCSV(file);
            return readZoneFile(csv, latitude, longitude);
        }
        throw new FileEmptyException("The file is empty");
    }

    public List<PointZone> readZoneFile(List<String[]> csv, String latitude, String longitude) {
        String[] header = csv.get(0);
        List<PointZone> pointZones = new ArrayList<>();

        for (int i=1; i<csv.size(); i++) {
            PointZone pointZone = new PointZone();
            for (int j=0; j< header.length; j++) {
                if (latitude.equals(header[j])) {
                    pointZone.setLat(Double.parseDouble(csv.get(i)[j]));
                } else if (longitude.equals(header[j])) {
                    pointZone.setLng(Double.parseDouble(csv.get(i)[j]));
                }
                pointZones.add(pointZone);
            }
        }
        return pointZones;
    }
}
