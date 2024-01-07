package com.Microblogs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalApiException extends RuntimeException{

    public InternalApiException(String message) {
        super(message);
    }
}
