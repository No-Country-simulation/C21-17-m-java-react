package com.microservice.course.services;


import com.microservice.course.client.UserClient;
import com.microservice.course.controllers.dto.UserDTO;

import com.microservice.course.entities.Course;
import com.microservice.course.http.response.UserByCourseResponse;
import com.microservice.course.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired   // Inyectamos el userClient, de aca proviene la conexion con el microservicio User
    private UserClient userClient;

    @Override
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public UserByCourseResponse findUserByCourseId(Long idCourse) {

        // Consultamos el curso
        Course course = courseRepository.findById(idCourse).orElseThrow();

        // Obtenemos a todos los usuarios que tengan el curso id, aca se realiza la conexion con el microservicio USER
        List<UserDTO> userDTOList = userClient.findAllUserByCourse(course.getId());

        return UserByCourseResponse.builder()
                .courseName(course.getTitle())
                .description(course.getDescription())
                .userDTOList(userDTOList)
                .build();
    }
}
