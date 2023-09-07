package com.gerasimov.capstone.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(DuplicateUserException.class);


    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> handleRestaurantNotFoundException(DuplicateUserException duplicateUserException) {
        // Log the exception
        // You can use a logging framework like Logback or Log4j
        logger.error("Duplicate user exception: {}", duplicateUserException.getMessage());

        // Return a custom error response
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(duplicateUserException.getMessage());
    }

    // Add more exception handlers as needed
}
