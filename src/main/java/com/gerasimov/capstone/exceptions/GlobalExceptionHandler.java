//package com.gerasimov.capstone.exceptions;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//    @ExceptionHandler(RestaurantException.class)
//    public String handleRestaurantException(RestaurantException e) {
//        log.error("Exception: ", e);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//    }
//
//}
//
//
//
