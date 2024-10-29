package com.microservice.user.microservice_user.handlers;

import com.microservice.user.microservice_user.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserExceptionHandler {

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
