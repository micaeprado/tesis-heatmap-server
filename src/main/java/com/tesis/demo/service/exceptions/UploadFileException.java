package com.tesis.demo.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
public class UploadFileException extends RuntimeException {

    public UploadFileException(String message, Exception e) {
        super(message, e);
    }

}
