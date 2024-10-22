package com.microservice.course.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.microservice.course.entities.Module;

@Repository
public interface ModuleRepository extends CrudRepository<Module, Long> {
}
