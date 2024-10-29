package com.microservice.course.services;

import com.microservice.course.entities.Module;
import com.microservice.course.http.response.CourseByModuleResponse;

import java.util.List;
import java.util.Map;

public interface IModuleService {


    List<Module> findAll();

    Map<String, String> save(Module module, Long id);

    CourseByModuleResponse findById(Long idModule);

    Map<String, String> deleteModule(Long courseId, Long moduleId, Long userId);
}
