package com.tesis.demo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tesis.demo.model.Incident;
import com.tesis.demo.model.dto.IncidentDto;
import com.tesis.demo.service.IncidentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/incident")
public class IncidentController {

    @Autowired
    protected IncidentService incidentService;

    @Autowired
    protected ModelMapper modelMapper;

    @GetMapping
    public List<IncidentDto> getIncidents(){
        List<IncidentDto> incidentsDto = incidentService.getAllIncidents()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return incidentsDto;
    }

    @GetMapping("/page/{page}")
    public Page<IncidentDto> getIncidents(@PathVariable Integer page){
        List<IncidentDto> incidentsDto = incidentService.getAllIncidents(PageRequest.of(page, 5))
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(incidentsDto ,  PageRequest.of(page, 5), incidentService.getAllIncidents().size());
    }


    private IncidentDto convertToDto(Incident incident) {
        IncidentDto incidentDto = modelMapper.map(incident, IncidentDto.class);
        Long diffTime = (incident.getEndTime().getTime() - incident.getStartTime().getTime()) / 1000;
        incidentDto.setSeconds(diffTime);

        return incidentDto;
    }
}
