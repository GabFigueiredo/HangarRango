package com.igrejacristahangar.cantina.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseBusinessException {

    public ResourceNotFoundException(String message, String field) {
        super(message, field, HttpStatus.NOT_FOUND);
    }

}
