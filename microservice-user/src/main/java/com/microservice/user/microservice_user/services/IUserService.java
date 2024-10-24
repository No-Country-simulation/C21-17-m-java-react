package com.microservice.user.microservice_user.services;

import com.microservice.user.microservice_user.entities.User;
import com.microservice.user.microservice_user.entities.UserDto;

import java.util.List;

public interface IUserService {

    String saveUser(User credential);

    String loginUser(UserDto userDto);

    void validateToken(String token);

    List<User> getAllUsers();
}
