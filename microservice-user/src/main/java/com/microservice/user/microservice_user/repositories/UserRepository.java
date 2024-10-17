package com.microservice.user.microservice_user.repositories;

import com.microservice.user.microservice_user.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    boolean existsByName(String name);

    Optional<User> findByName(String name);

    List<User> findAllByCourseId(Long courseId);
}
