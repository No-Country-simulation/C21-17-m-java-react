package com.microservice.course.services;

import com.microservice.course.client.UserClient;
import com.microservice.course.client.UserClientImpl;
import com.microservice.course.controllers.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservice.course.exceptions.UnauthorizedActionException;

@Service
public class AuthorizationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    @Autowired
    private UserClientImpl userClient;

    /**
     * Verifica si un usuario tiene el rol especificado.
     *
     * @param userId ID del usuario.
     * @param requiredRole Rol requerido (por ejemplo, "ROLE_ADMIN").
     * @throws UnauthorizedActionException si el usuario no tiene el rol necesario.
     */
    public void checkUserRole(Long userId, String requiredRole) {
        UserDTO user = userClient.getUserById(userId);

        if (user == null || user.getRoleId() == null || !userClient.hasRole(user.getRoleId().getId(), requiredRole)) {
            logger.error("El usuario {} no tiene permisos de rol {}.", user != null ? user.getName() : "desconocido", requiredRole);
            throw new UnauthorizedActionException();
        }
    }
}
