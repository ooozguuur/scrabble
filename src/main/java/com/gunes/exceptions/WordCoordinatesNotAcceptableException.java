package com.gunes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class WordCoordinatesNotAcceptableException extends RuntimeException {

    public WordCoordinatesNotAcceptableException(String exception) {
        super(exception);
    }

}
