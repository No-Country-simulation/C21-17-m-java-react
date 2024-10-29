package com.microservice.course.services;

import com.microservice.course.entities.Course;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICourseService {

    List<Course> findAll();

    Map<String, String> save(Course course, Long id);
//
//    UserByCourseResponse findUserByCourseId(Long idCourse);

    Map<String, String> deleteCourse(Long courseId, Long userId);

    Optional<Course> findCourseById(Long id);
}
