package com.ricardo.traker.exception.handlers;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.exception.TrakerException;
import com.ricardo.traker.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class HandlerException extends Throwable{
    private static final long serialVersionUID = 1L;


    @ExceptionHandler(TrakerException.class)
    public ResponseEntity<?> handleTrakerException(TrakerException e, HttpServletRequest httpServletRequest) {
        // log exception
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ApiErrorResponse(
                        LocalDateTime.now(),
                        e.getHttpStatus().value(),
                        e.getHttpStatus().getReasonPhrase(),
                        e.getMessage(),
                        httpServletRequest.getServletPath()));
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleUserException(UserException e, HttpServletRequest httpServletRequest) {
        // log exception
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        e.getMessage(),
                        httpServletRequest.getServletPath()));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleServiceException(ServiceException e, HttpServletRequest httpServletRequest) {
        // log exception
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        e.getMessage(),
                        httpServletRequest.getServletPath()));
    }
}
