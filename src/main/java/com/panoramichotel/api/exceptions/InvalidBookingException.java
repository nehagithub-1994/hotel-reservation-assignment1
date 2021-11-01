package com.panoramichotel.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidBookingException extends Exception {

    InvalidBookingException() {

    }

    public InvalidBookingException(String message) {
        super(message);
    }
}
