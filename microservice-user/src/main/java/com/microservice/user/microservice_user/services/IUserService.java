package com.microservice.user.microservice_user.services;

import com.microservice.user.microservice_user.entities.User;

public interface IUserService {

    String saveUser(User credential);

    String generateToken(String username);

    void validateToken(String token);
}
