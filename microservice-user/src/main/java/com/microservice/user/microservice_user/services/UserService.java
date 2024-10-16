package com.microservice.user.microservice_user.services;

import com.microservice.user.microservice_user.entities.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User save(User user);
}
