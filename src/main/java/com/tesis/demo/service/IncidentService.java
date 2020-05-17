package com.tesis.demo.service;

import com.tesis.demo.model.Incident;
import com.tesis.demo.model.dto.IncidentDto;
import com.tesis.demo.repository.IncidentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncidentService {

    @Autowired
    protected IncidentRepository incidentRepository;
    @Autowired
    protected ModelMapper modelMapper;

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Page<Incident> getAllIncidents(Pageable pageable){
        return incidentRepository.findAll(pageable);
    }


    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id).get();
    }

    /* public Long getTotalSecondsIncidents() {
        List<Incident> incidents = incidentRepository.findAll();
        Long total = 0L;

        for (Incident incident : incidents) {
            total += (incident.getEndTime().getTime() - incident.getStartTime().getTime()) / 1000;
        }
        return total;
    }*/

}
