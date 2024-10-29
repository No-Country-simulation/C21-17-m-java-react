package com.microservice.course.client;

import com.microservice.course.controllers.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserClientImpl {

    @Autowired
    private UserClient userClient;

    public UserDTO getUserById(Long id) {
        return userClient.findUserById(id); // Utilizamos el endpoint para obtener el usuario
    }

    public boolean hasRole(Long id, String requiredRole) {
        try {
            UserDTO user = userClient.findUserById(id);

            // Verifica si el rol del usuario coincide con el rol requerido
            return user.getRoleId() != null && user.getRoleId().getName().equals(requiredRole);

        } catch (Exception e) {
            // Manejo de errores: puedes registrar el error o lanzar una excepción personalizada
            throw new RuntimeException("Error al verificar el rol del usuario", e);
        }
    }
}
