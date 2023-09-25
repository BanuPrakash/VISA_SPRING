package com.visa.shopapp.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("timestamp", new Date());
        List<String> errors= ex.getFieldErrors().stream()
                .map(exception -> exception.getDefaultMessage()).collect(Collectors.toList());
        map.put("errors", errors);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    }
