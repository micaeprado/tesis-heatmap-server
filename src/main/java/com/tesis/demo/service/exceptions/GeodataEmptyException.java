package com.tesis.demo.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="There is no data")
public class GeodataEmptyException extends RuntimeException {

    public GeodataEmptyException(String message) {
        super(message);
    }

}
