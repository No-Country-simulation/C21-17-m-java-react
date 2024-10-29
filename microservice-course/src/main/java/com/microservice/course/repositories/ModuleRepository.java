package com.microservice.course.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.microservice.course.entities.Module;

import java.util.Optional;

@Repository
public interface ModuleRepository extends CrudRepository<Module, Long> {
    Optional<Module> findByTitle(String title);
}
