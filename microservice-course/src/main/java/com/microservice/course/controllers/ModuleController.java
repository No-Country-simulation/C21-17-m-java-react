package com.microservice.course.controllers;



import com.microservice.course.entities.Module;
import com.microservice.course.services.IModuleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course/module")
public class ModuleController {

    @Autowired
    private IModuleService moduleService;

    @PostMapping("/{id}")
    public ResponseEntity<?> create(@Valid @RequestBody Module module, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(module, id));
    }

    @DeleteMapping("/{courseId}/{moduleId}/{userId}") // Asegúrate de usar nombres descriptivos en la ruta
    public ResponseEntity<?> delete(@PathVariable Long courseId, @PathVariable Long moduleId, @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(moduleService.deleteModule(courseId,moduleId,userId));
    }

    @GetMapping()
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(200).body(moduleService.findAll());
    }

    @GetMapping("/modules/{moduleId}")
    public ResponseEntity<?> findById(@PathVariable Long moduleId){
        return ResponseEntity.ok(moduleService.findById(moduleId));
    }
}