package com.bol.interview.mancalaservice.configuration;

import com.bol.interview.common.exceptions.MancalaGameException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("An internal server error occurred", e);
        return new ResponseEntity<>("An internal server error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MancalaGameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleCustomException(MancalaGameException e) {
        return new ResponseEntity<>("Bad request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
