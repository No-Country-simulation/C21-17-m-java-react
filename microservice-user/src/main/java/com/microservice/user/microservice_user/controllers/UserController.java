package com.microservice.user.microservice_user.controllers;

import com.microservice.user.microservice_user.entities.User;
import com.microservice.user.microservice_user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> users() {
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) { // Verificar, posee errores
        User createdUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/search-by-course/{idCourse}")
    public ResponseEntity<?> findByIdCourse(@PathVariable Long idCourse){
        return ResponseEntity.ok(userService.findByCourseId(idCourse));
    }
}
