package com.microservice.user.microservice_user.config;


import com.microservice.user.microservice_user.entities.User;
import com.microservice.user.microservice_user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> credential = repository.findByName(username);

        // Usamos una lambda explícita para evitar confusiones de tipo
        return credential
                .map(CustomUserDetails::new)  // Convertimos explícitamente User a CustomUserDetails
                .orElseThrow(() -> new UsernameNotFoundException("user not found with name: " + username));
    }
}
