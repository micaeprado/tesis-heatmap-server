package com.tesis.demo.controller;

import com.tesis.demo.model.FileData;
import com.tesis.demo.model.Filter;
import com.tesis.demo.model.WeightedLoc;
import com.tesis.demo.model.dto.LayerDto;
import com.tesis.demo.service.AnalysisService;
import com.tesis.demo.service.FileDataService;
import com.tesis.demo.service.FilterService;
import com.tesis.demo.service.GeodataService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    protected final ModelMapper modelMapper;
    protected final GeodataService geodataService;
    protected final FileDataService fileDataService;
    protected final FilterService filterService;

    /*@GetMapping("/{idFunction}")
    public List<WeightedLoc> getElementsAnalyzed(@PathVariable Integer idFunction){
        return null;
        return analysisService.getElementsAnalyzed(idFunction);
    }*/

    @GetMapping("/file-data")
    public List<FileData> getAllFileData() {
        return fileDataService.getAllFileData();
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

  /*  @GetMapping("/elements")
    public List<Geodata> getFilteredElements(@RequestParam("file-name") String fileName,
                                             @RequestParam("field") String field,
                                             @RequestParam("field-value") String fieldValue) {
        return geodataService.getFilteredElements(fileName, field, fieldValue);
    }*/

    @GetMapping("/functions")
    public Set<String> getFunctions() {
        return analysisService.getFunctions();
    }


    @PostMapping("/map")
    public List<WeightedLoc> getMapElements(@RequestBody LayerDto layer) {
        return analysisService.getMapElements(layer);
    }

   /* @GetMapping("/map")
    public List<WeightedLoc> getMapElements(@RequestParam("file-name") String fileName,
                                            @RequestParam("field-filter") String fieldFilter,
                                            @RequestParam("field-value-filter") String fieldValueFilter,
                                            @RequestParam("function") String function,
                                            @RequestParam("field-value-function") String fieldValueFilterFunction,
                                            @RequestParam("zone") Long zone) {
        return analysisService.getMapElements(fileName, fieldFilter, fieldValueFilter, function, fieldValueFilterFunction, zone);
    }*/

}
