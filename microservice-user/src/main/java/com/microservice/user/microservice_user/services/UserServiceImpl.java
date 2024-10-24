package com.microservice.user.microservice_user.services;

import com.microservice.user.microservice_user.entities.Role;
import com.microservice.user.microservice_user.entities.User;
import com.microservice.user.microservice_user.repositories.RoleRepository;
import com.microservice.user.microservice_user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(User credential){
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));

        Role userRole = roleRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("El rol de usuario no existe"));

        credential.setRole(userRole);

        userRepository.save(credential);
        return "Usuario agregado en el sistema";
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
