package com.igrejacristahangar.cantina.exceptions;

import org.springframework.http.HttpStatus;

public class InactiveProductException extends BaseBusinessException {

    public InactiveProductException(String message, String field) {
        super(message, field, HttpStatus.NOT_FOUND);
    }
}
