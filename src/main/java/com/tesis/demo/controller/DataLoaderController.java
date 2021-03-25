package com.tesis.demo.controller;

import com.tesis.demo.service.DataLoaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/data-loader")
@Slf4j
public class DataLoaderController {

    protected final DataLoaderService dataLoaderService;

    public DataLoaderController(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }

    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("latitude") String latitude,
                                        @RequestParam("longitude") String longitude) {
        Map<String, Object> response = new HashMap<>();

        if(!file.isEmpty()){
            try {
                dataLoaderService.uploadFile(file, latitude, longitude);
                response.put("mensaje", "Has subido correctamente el CSV: "+file.getOriginalFilename());
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } catch (Exception e) {
                response.put("mensaje", "Error al subir el CSV");
                response.put("error", e.getMessage());
                e.printStackTrace();
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        response.put("mensaje", "El CSV esta vacio");
        response.put("error", "El CSV esta vacio");
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/read-file")
    public ResponseEntity<String[]> readFileAndGetHeader(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(dataLoaderService.readFileAndGetHeader(file));
    }

    @PostMapping("/upload-zones-file")
    public ResponseEntity<?> uploadZoneFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("latitude") String latitude,
                                        @RequestParam("longitude") String longitude,
                                        @RequestParam("name") String name,
                                        @RequestParam("name") String description) {
        Map<String, Object> response = new HashMap<>();

        if(!file.isEmpty()){
            try {
                dataLoaderService.uploadZoneFile(file, latitude, longitude, name, description);
                response.put("mensaje", "Se han creado correctamente las zonas: "+file.getOriginalFilename());
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } catch (Exception e) {
                response.put("mensaje", "Error al cargar el archivo");
                response.put("error", e.getMessage());
                e.printStackTrace();
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        response.put("mensaje", "El archivo esta vacio");
        response.put("error", "El archivo esta vacio");
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
