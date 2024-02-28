package com.ricardo.traker.exception;

import org.springframework.http.HttpStatus;

public class TrakerException extends Exception {

    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;
    public TrakerException(String message) {
        super(message);
    }
    public TrakerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrakerException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
    public TrakerException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
