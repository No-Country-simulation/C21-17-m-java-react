package com.microservice.course.handlers;

import com.microservice.course.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice

public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> courseNotFound(CourseNotFoundException ex , WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("El curso no existe",ex.getMessage() );
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String, String>> genericError(Exception ex , WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error","Unexpected error" );
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CourseAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> courseExist(CourseAlreadyExistsException ex , WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("El curso ya existe en la base de datos",ex.getMessage() );
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Map<String, String>> courseNotFound(UnauthorizedActionException ex , WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("El usuario no posee autorizacion para dicha accion",ex.getMessage() );
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotExistsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Map<String, String>> courseNotFound(UserNotExistsException ex , WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("El usuario no existe en el sistema",ex.getMessage() );
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ModuleNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Map<String, String>> courseNotFound(ModuleNotFoundException ex , WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("El modulo no existe en el curso",ex.getMessage() );
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }
}
