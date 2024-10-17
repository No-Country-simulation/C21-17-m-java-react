package com.microservice.course.repositories;

import com.microservice.course.entities.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    boolean existsByTitle(String title);

    Optional<Course> findByTitle(String title);
}
