package com.microservice.course.services;

import com.microservice.course.entities.Course;
import com.microservice.course.http.response.UserByCourseResponse;

import java.util.List;

public interface ICourseService {

    List<Course> findAll();

    Course save(Course course);

    UserByCourseResponse findUserByCourseId(Long idCourse);
}
