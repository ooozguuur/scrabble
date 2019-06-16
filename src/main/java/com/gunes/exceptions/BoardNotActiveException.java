package com.gunes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class BoardNotActiveException extends RuntimeException {

    public BoardNotActiveException(String exception) {
        super(exception);
    }

}
