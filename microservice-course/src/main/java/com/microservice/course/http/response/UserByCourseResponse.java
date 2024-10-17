package com.microservice.course.http.response;

import com.microservice.course.controllers.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Clase que mapea respuesta
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserByCourseResponse {

    // Lo que hacemos con dicha clase es personalizar la respuesta para que cuando se utilize el endpoint para buscar los cursos me de informacion extra

    private String courseName;
    private String description;
    private List<UserDTO> userDTOList;
}
