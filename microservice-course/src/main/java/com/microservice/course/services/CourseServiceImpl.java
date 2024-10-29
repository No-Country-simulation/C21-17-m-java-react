package com.microservice.course.services;



import com.microservice.course.client.UserClientImpl;
import com.microservice.course.controllers.dto.UserDTO;

import com.microservice.course.entities.Course;
import com.microservice.course.exceptions.CourseAlreadyExistsException;

import com.microservice.course.exceptions.CourseNotFoundException;
import com.microservice.course.exceptions.UnauthorizedActionException;

import com.microservice.course.exceptions.UserNotExistsException;
import com.microservice.course.repositories.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseRepository courseRepository;

       // Inyectamos el userClient, de aca proviene la conexion con el microservicio User
    @Autowired
    private UserClientImpl userClient;

    @Autowired
    private AuthorizationService authorizationService;


    private final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);



    @Override
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }


    @Override
    public Map<String, String> save(Course course, Long id) {
        try {

            UserDTO user = userClient.getUserById(id);
            authorizationService.checkUserRole(id, "ROLE_ADMIN");

            // Verificar si el curso ya existe
            Optional<Course> existingCourse = courseRepository.findByTitle(course.getTitle());
            if (existingCourse.isPresent()) {
                logger.error("Intento de creacion fallido: el curso {} ya existe.", course.getTitle());
                throw new CourseAlreadyExistsException();
            }

            courseRepository.save(course);
            logger.info("Curso {} agregado exitosamente.", course.getTitle());

            Map<String, String> messageResponse = new HashMap<>();
            messageResponse.put("Estado", "Curso agregado exitosamente");
            messageResponse.put("Horario de registro:", LocalDateTime.now().toString());

            return messageResponse;
        } catch (Exception e) {
            logger.error("Error al registrar el curso: {}", e.getMessage());
            throw e;
        }
    }



//    @Override
//    public UserByCourseResponse findUserByCourseId(Long idCourse) {
//        try {
//            // Consultamos el curso
//            Course course = courseRepository.findById(idCourse).orElseThrow();
//
//            // Obtenemos a todos los usuarios que tengan el curso id, aca se realiza la conexion con el microservicio USER
//            List<UserDTO> userDTOList = userClient.findAllUserByCourse(course.getId());
//
//            return UserByCourseResponse.builder()
//                    .courseName(course.getTitle())
//                    .description(course.getDescription())
//                    .userDTOList(userDTOList)
//                    .build();
//        } catch (Throwable e) {
//            throw new CourseNotFoundException();
//        }
//    }


    @Override
    public Map<String, String> deleteCourse(Long courseId, Long userId) {
        try {
            // Obtener el usuario y verificar sus permisos
            UserDTO user = userClient.getUserById(userId);

            if (user == null){
                throw new UserNotExistsException();
            }

            authorizationService.checkUserRole(userId, "ROLE_ADMIN");

            // Verificar si el curso existe
            if (!courseRepository.existsById(courseId)) {
                logger.error("Intento de eliminación fallido: el curso con ID {} no existe.", courseId);
                throw new CourseNotFoundException();
            }

            // Eliminar el curso
            courseRepository.deleteById(courseId);
            logger.info("Curso con ID {} eliminado exitosamente por el usuario {}.", courseId, user.getName());

            Map<String, String> messageResponse = new HashMap<>();
            messageResponse.put("Estado", "Curso eliminado exitosamente");
            messageResponse.put("Horario de eliminación:", LocalDateTime.now().toString());

            return messageResponse;
        } catch (Exception e) {
            logger.error("Error al eliminar el curso con ID {}: {}", courseId, e.getMessage());
            throw e; // Lanzar la excepción para que el controlador pueda manejarla
        }
    }

    @Override
    public Optional<Course> findCourseById(Long id) {
       return courseRepository.findById(id);
    }
}
