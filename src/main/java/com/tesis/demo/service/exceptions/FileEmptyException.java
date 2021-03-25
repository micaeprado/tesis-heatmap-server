package com.tesis.demo.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="The file is empty")
public class FileEmptyException extends RuntimeException {

    public FileEmptyException(String message) {
        super(message);
    }

}
