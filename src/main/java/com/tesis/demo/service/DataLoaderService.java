package com.tesis.demo.service;

import com.tesis.demo.utils.CsvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataLoaderService {

    private final GeodataService geodataService;
    private final FileDataService fileDataService;

    public void uploadFile(MultipartFile file, String latitude, String longitude, String separator, String quote) throws Exception {
        List<String[]> csv = CsvUtil.readCsv(new InputStreamReader(file.getInputStream()), separator, quote);
        int latPos = 0;
        int lngPos = 0;

        String[] header = csv.get(0);
        List<String> headers = new ArrayList<>();

        for (int i=0; i<header.length; i++) {
            if (header[i].equals(latitude)){
                latPos = i;
            } else if (header[i].equals(longitude)){
                lngPos = i;
            } else {
                headers.add(header[i]);
            }

        }
        fileDataService.saveFileData(file.getOriginalFilename(), headers);

        for (int i=1; i<csv.size(); i++) {
            Map<String, String> fields = new HashMap<>();
            for (int j=0; j<header.length; j++) {
                if (j != latPos && j != lngPos) {
                    if (!csv.get(i)[j].isEmpty()) {
                        fields.put(header[j], csv.get(i)[j]);
                    }
                }
            }
            geodataService.saveGeodata(file.getOriginalFilename(), Double.parseDouble(csv.get(i)[latPos]),
                    Double.parseDouble(csv.get(i)[lngPos]), fields);
        }
    }

}
