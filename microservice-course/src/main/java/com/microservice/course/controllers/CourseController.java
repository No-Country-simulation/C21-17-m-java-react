package com.microservice.course.controllers;


import com.microservice.course.entities.Course;
import com.microservice.course.services.ICourseService;
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
     private static Logger logger = LoggerFactory.getLogger(CourseController.class);

    @GetMapping()
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(200).body(courseService.findAll());
    }


    @GetMapping("/courses/{courseId}") // ENDPOINT expone un curso por id
    public ResponseEntity<?> findStudentsByIdCourse(@PathVariable Long courseId){
        return ResponseEntity.ok(courseService.findUserByCourseId(courseId));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Course course){
        logger.info(course.toString());
        Course createdCourse = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @DeleteMapping("/{id}")//ENDPOINT para eliminar cursos
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
}
}
