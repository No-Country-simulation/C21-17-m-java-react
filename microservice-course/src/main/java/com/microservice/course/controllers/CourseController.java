package com.microservice.course.controllers;


import com.microservice.course.client.UserClientImpl;
import com.microservice.course.controllers.dto.UserDTO;
import com.microservice.course.entities.Course;
import com.microservice.course.services.ICourseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/v1/course")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @Autowired
    private UserClientImpl userService;


    private static Logger logger = LoggerFactory.getLogger(CourseController.class);

    @GetMapping()
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(200).body(courseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCourseById(@PathVariable Long id){
        return ResponseEntity.status(200).body(courseService.findCourseById(id));
    }

//    @GetMapping("/courses/{courseId}") // ENDPOINT expone un curso por id
//    public ResponseEntity<?> findStudentsByIdCourse(@PathVariable Long courseId){
//        return ResponseEntity.ok(courseService.findUserByCourseId(courseId));
//    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        logger.info("Buscando usuario con id: {}", id);
        try {
            UserDTO user = userService.getUserById(id);
            logger.info("Usuario encontrado: {}", user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error fetching user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{id}") // Agregar el ID del usuario en la URL
    public ResponseEntity<?> create(@Valid @RequestBody Course course, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course,id));
    }

    @DeleteMapping("/{courseId}/{userId}") // Asegúrate de usar nombres descriptivos en la ruta
    public ResponseEntity<?> delete(@PathVariable Long courseId, @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(courseService.deleteCourse(courseId,userId));
    }

}

//    @DeleteMapping("/{id}")//ENDPOINT para eliminar cursos
//    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
//
//
//        courseService.deleteCourse(id);
//        return ResponseEntity.noContent().build();
//}

