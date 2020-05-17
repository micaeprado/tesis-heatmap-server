package com.tesis.demo.model.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tesis.demo.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class LocationConverterJson implements AttributeConverter<Location, String> {

    Logger logger = LoggerFactory.getLogger(LocationConverterJson.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Location meta) {
        try {
            return objectMapper.writeValueAsString(meta);
        } catch (JsonProcessingException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public Location convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Location.class);
        } catch (IOException ex) {
            logger.error("Unexpected IOEx decoding json from database: " + dbData);
            logger.error(ex.getMessage());
            return null;
        }
    }

}
