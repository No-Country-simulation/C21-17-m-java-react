package com.microservice.user.microservice_user.controllers;

import com.microservice.user.microservice_user.entities.User;
import com.microservice.user.microservice_user.services.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<?> users() {

        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/search-by-course/{courseId}")
    public ResponseEntity<?> searchByCourse(@PathVariable int courseId) {
        return ResponseEntity.ok().body(Collections.emptyList());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user) { // Verificar, posee errores
        User createdUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
