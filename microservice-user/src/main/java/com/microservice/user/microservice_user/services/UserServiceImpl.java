package com.microservice.user.microservice_user.services;

import com.microservice.user.microservice_user.entities.User;
import com.microservice.user.microservice_user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAll(){
        return (List<User>) repository.findAll();
    }
}
