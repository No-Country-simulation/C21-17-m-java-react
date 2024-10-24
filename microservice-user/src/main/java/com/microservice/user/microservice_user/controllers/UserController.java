package com.microservice.user.microservice_user.controllers;

import com.microservice.user.microservice_user.entities.User;
import com.microservice.user.microservice_user.entities.UserDto;
import com.microservice.user.microservice_user.services.IUserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private IUserService service;


    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();
        return ResponseEntity.ok(users); // Retorna una respuesta con la lista de usuarios
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDto userDto, BindingResult result) {
        // Verificar si hay errores de validación
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.loginUser(userDto));

    }

    @GetMapping("/search-by-course/{courseId}")
    public ResponseEntity<?> searchByCourse(@PathVariable int courseId) {
        return ResponseEntity.ok().body(Collections.emptyList());

    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }

    // Método de validación para manejar errores de campo
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
