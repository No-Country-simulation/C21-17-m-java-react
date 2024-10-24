package com.microservice.course.http.response;

import com.microservice.course.controllers.dto.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseByModuleResponse{


    private String moduleName;
    private String description;
    private CourseDTO course;
}