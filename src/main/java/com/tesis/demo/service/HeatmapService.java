package com.tesis.demo.service;

import com.tesis.demo.model.Heatmap;
import com.tesis.demo.model.dto.GeodataDto;
import com.tesis.demo.model.dto.HeatmapDto;
import com.tesis.demo.model.mapper.GeodataMapper;
import com.tesis.demo.model.mapper.HeatmapMapper;
import com.tesis.demo.repository.HeatmapRepository;
import com.tesis.demo.service.exceptions.GeodataEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HeatmapService {

    private final HeatmapRepository heatmapRepository;

    public Heatmap save(Heatmap map) {
        return heatmapRepository.save(map);
    }

    public Page<HeatmapDto> getHeatmapPerPage(Pageable pageable) {
        return heatmapRepository.findAll(pageable)
                .map(HeatmapMapper::toDto);
    }

    public HeatmapDto getHeatmap(String id) {
        Heatmap heatmap = heatmapRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return HeatmapMapper.toDto(heatmap);
    }

    public ResponseEntity<Resource> downloadHeatmapDataCSV(String id) throws IOException {
        Heatmap heatmap = heatmapRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        List<GeodataDto> geodata = GeodataMapper.toDTOList(heatmap.getGeodata());
        if (geodata.size() == 0) {
            throw new GeodataEmptyException("The heatmap is empty");
        }

        List<List<String>> data = getData(geodata);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try (PrintWriter pw = new PrintWriter(stream)) {
            data.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }

        InputStream inputStream = new ByteArrayInputStream(stream.toByteArray());
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s_%s%s", heatmap.getName().replaceAll(" ", "-").toLowerCase(), LocalDateTime.now().format(formatter), ".csv"))
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(new InputStreamResource(inputStream));
        } finally {
            stream.close();
        }
    }

    public List<List<String>> getData(List<GeodataDto> geodata) {
        List<List<String>> dataLines = new ArrayList<>();
        setHeader(dataLines, geodata.get(0));

        geodata.forEach(data -> {
            List<String> line = new ArrayList<>();
            line.add(String.valueOf(data.getLng()));
            line.add(String.valueOf(data.getLat()));
            for(Map.Entry<String, String> entry : data.getFields().entrySet()) {
                line.add(entry.getValue());
            }
            dataLines.add(line);
        });

        return dataLines;
    }

    public void setHeader(List<List<String>> dataLines, GeodataDto data) {
        List<String> line = new ArrayList<>();
        line.add("Lng");
        line.add("lat");
        for(Map.Entry<String, String> entry : data.getFields().entrySet()) {
            line.add(entry.getKey());
        }
        dataLines.add(line);
    }

    public String convertToCSV(List<String> data) {
        return data.stream()
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
