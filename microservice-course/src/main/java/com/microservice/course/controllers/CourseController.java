package com.microservice.course.controllers;

import com.microservice.course.entities.Course;
import com.microservice.course.services.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Course course){
        Course createdCourse = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @GetMapping()
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(200).body(courseService.findAll());
    }

    @GetMapping("/search-user/{courseId}") // ENDPOINT conecta microservicios
    public ResponseEntity<?> findStudentsByIdCourse(@PathVariable Long courseId){
        return ResponseEntity.ok(courseService.findUserByCourseId(courseId));
    }


    @DeleteMapping("/{id}")//ENDPOINT para eliminar cursos
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
}
}
