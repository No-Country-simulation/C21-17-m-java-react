package com.microservice.course.controllers;


import com.microservice.course.entities.Module;
import com.microservice.course.services.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/module")
public class ModuleController {

    @Autowired
    private IModuleService moduleService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Module module){
        Module createdModule = moduleService.save(module);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdModule);
    }

    @GetMapping()
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(200).body(moduleService.findAll());
    }

    @GetMapping("/modules/{moduleId}")
    public ResponseEntity<?> findById(@PathVariable Long moduleId){
        return ResponseEntity.ok(moduleService.findById(moduleId));
    }


    @DeleteMapping("/{id}")//ENDPOINT para eliminar cursos
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }
}