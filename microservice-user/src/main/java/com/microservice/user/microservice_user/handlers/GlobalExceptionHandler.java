package com.microservice.user.microservice_user.handlers;

import com.microservice.user.microservice_user.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
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

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "El usuario ya se encuentra registrado");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT); // Devuelve 409 Conflict en lugar de 403
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Usuario no encontrado");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // Devuelve 404 Not Found
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotFoundException(RoleNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Rol no encontrado");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // Devuelve 400 Bad Request
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, String>> handleInvalidTokenException(InvalidTokenException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Token inválido");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); // Devuelve 401 Unauthorized
    }

    @ExceptionHandler(UserNotAuthorized.class)
    public ResponseEntity<Map<String, String>> handleUserNotAuthorizedException(UserNotAuthorized ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Usuario no registrado");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
