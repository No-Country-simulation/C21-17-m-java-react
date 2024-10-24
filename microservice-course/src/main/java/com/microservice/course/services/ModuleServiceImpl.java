package com.microservice.course.services;

import com.microservice.course.controllers.dto.CourseDTO;
import com.microservice.course.entities.Course;
import com.microservice.course.entities.Module;
import com.microservice.course.http.response.CourseByModuleResponse;
import com.microservice.course.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public class ModuleServiceImpl implements IModuleService {

    @Autowired
    private ModuleRepository moduleRepository;


    @Override
    public List<Module> findAll() {
        return (List<Module>) moduleRepository.findAll();
    }

    @Override
    public Module save(Module module) {
        return moduleRepository.save(module);
    }

    @Override
    public CourseByModuleResponse findById(Long idModule) {
        Module module = moduleRepository.findById(idModule).orElseThrow();
        Course course = module.getCourse();

        return CourseByModuleResponse.builder()
                .moduleName(module.getTitle())
                .description(module.getDescription())
                .course(CourseDTO.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .description(course.getDescription())
                        .build()
                )
                .build();
    }


    @Override
    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);

    }


}
