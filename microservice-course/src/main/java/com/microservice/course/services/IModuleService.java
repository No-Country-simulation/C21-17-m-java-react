package com.microservice.course.services;

import com.microservice.course.entities.Module;
import com.microservice.course.http.response.CourseByModuleResponse;

import java.util.List;

public interface IModuleService {


    List<Module> findAll();

   Module save(Module module);

    CourseByModuleResponse findById(Long idModule);

    void deleteModule(Long id);
}
