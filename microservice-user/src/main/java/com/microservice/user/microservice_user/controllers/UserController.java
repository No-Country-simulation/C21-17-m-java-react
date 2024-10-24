package com.microservice.user.microservice_user.controllers;

import com.microservice.user.microservice_user.entities.User;
import com.microservice.user.microservice_user.entities.UserDto;
import com.microservice.user.microservice_user.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;



@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private IUserService service;


    @Autowired
    private AuthenticationManager authenticationManager;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public String addNewUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody UserDto userDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getName(), userDto.getPassword()));
        if (authenticate.isAuthenticated()) {
            return service.generateToken(userDto.getName());
        } else {
            throw new RuntimeException("invalid access");
        }
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
}
