package com.microservice.course.repositories;

import com.microservice.course.entities.Video;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends CrudRepository<Video, Long> {
    Optional<Video> findByTitle(String title);
}
