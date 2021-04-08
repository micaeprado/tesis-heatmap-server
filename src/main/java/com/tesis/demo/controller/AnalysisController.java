package com.tesis.demo.controller;

import com.tesis.demo.model.FileData;
import com.tesis.demo.model.Filter;
import com.tesis.demo.model.dto.MapDto;
import com.tesis.demo.model.dto.WeightedLocDto;
import com.tesis.demo.service.AnalysisService;
import com.tesis.demo.service.FileDataService;
import com.tesis.demo.service.FilterService;
import com.tesis.demo.service.GeodataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    protected final AnalysisService analysisService;
    protected final GeodataService geodataService;
    protected final FileDataService fileDataService;
    protected final FilterService filterService;

    @GetMapping("/file-data")
    public List<FileData> getAllFileData() {
        return fileDataService.getAllFileData();
    }

    @GetMapping("/file-data/page/{page}")
    public Page<FileData> getFilesPerPage(@PathVariable Integer page) {
        return fileDataService.getFilesPerPage(PageRequest.of(page, 5));
    }

    @GetMapping("/filter")
    public List<String> getFilters(@RequestParam("file-name") String fileName,
                                   @RequestParam("header") String header) {
        return geodataService.getFiltersByHeader(fileName, header);
    }

    @GetMapping("/filters")
    public List<Filter> getFilters() {
        return filterService.getAllFilters();
    }

    @GetMapping("/functions")
    public Set<String> getFunctions() {
        return analysisService.getFunctions();
    }


    @PostMapping("/map")
    public List<WeightedLocDto> createMap(@RequestBody MapDto map) {
        return analysisService.createMap(map);
    }

}
