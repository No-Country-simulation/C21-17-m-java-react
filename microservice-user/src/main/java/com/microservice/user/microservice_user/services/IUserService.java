package com.microservice.user.microservice_user.services;

import com.microservice.user.microservice_user.entities.User;
import com.microservice.user.microservice_user.entities.UserDto;

import java.util.List;
import java.util.Map;

public interface IUserService {

    Map<?,?>  saveUser(User credential);

    Map<?,?> loginUser(UserDto userDto);

    List<User> getAllUsers();

    UserDto findUserById(Long id);
}
