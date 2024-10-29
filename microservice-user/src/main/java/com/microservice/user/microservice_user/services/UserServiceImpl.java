package com.microservice.user.microservice_user.services;

import com.microservice.user.microservice_user.entities.Role;
import com.microservice.user.microservice_user.entities.User;
import com.microservice.user.microservice_user.entities.UserDto;
import com.microservice.user.microservice_user.exceptions.RoleNotFoundException;
import com.microservice.user.microservice_user.exceptions.UserAlreadyExistsException;
import com.microservice.user.microservice_user.exceptions.UserNotAuthorized;
import com.microservice.user.microservice_user.exceptions.UserNotFoundException;
import com.microservice.user.microservice_user.repositories.RoleRepository;
import com.microservice.user.microservice_user.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> saveUser(User credential) {
        try {
            // Verificar si el usuario ya existe
            Optional<User> existingUser = userRepository.findByName(credential.getName());
            if (existingUser.isPresent()) {
                logger.error("Intento de registro fallido: el usuario {} ya existe.", credential.getName());
                throw new UserAlreadyExistsException("El usuario " + credential.getName() + " ya existe en el sistema.");
            }

            // Codificar la contraseña
            credential.setPassword(passwordEncoder.encode(credential.getPassword()));

            // Asignar rol de usuario
            Role userRole = roleRepository.findById(2L)
                    .orElseThrow(() -> {
                        logger.error("Rol de usuario no encontrado para el registro de usuario.");
                        return new RoleNotFoundException("El rol de usuario no existe.");
                    });

            credential.setRole(userRole);

            // Guardar el usuario en la base de datos
            userRepository.save(credential);
            logger.info("Usuario {} agregado exitosamente.", credential.getName());

            Map<String, String> messageResponse = new HashMap<>();
            messageResponse.put("Estado","Usuario agregado exitosamente");
            messageResponse.put("Horario de registro:", LocalDateTime.now().toString());

            return messageResponse;
        } catch (Exception e) {
            logger.error("Error al registrar el usuario: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Map<String, String> loginUser(UserDto userDto) {
        // Verificar si el usuario existe
        Optional<User> optionalUser = userRepository.findByName(userDto.getName());
        if (optionalUser.isEmpty()) {
            logger.error("Intento de inicio de sesión fallido: el usuario {} no existe", userDto.getName());
            throw new UserNotFoundException("El usuario " + userDto.getName() + " no existe en el sistema");
        }

        User user = optionalUser.get(); // Obtener el objeto User

        try {
            // Intentar autenticar al usuario
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getName(), userDto.getPassword())
            );

            // Verificar si la autenticación fue exitosa
            if (!authenticate.isAuthenticated()) {
                logger.error("Acceso invalido para el usuario {}", user.getName());
                throw new UserNotAuthorized("El usuario " + user.getName() + " no esta autorizado");
            }

            // Generar y devolver el token JWT
            String token = jwtService.generateToken(user.getName());

            Map<String, String> messageResponse = new HashMap<>();

            messageResponse.put("Usuario", user.getName());
            messageResponse.put("Token", token);
            messageResponse.put("Inicio de sesion", LocalDateTime.now().toString());

            logger.info("Usuario {} inicio sesion exitosamente", user.getName());
            return messageResponse;
        } catch (RuntimeException e) {
            logger.error("Credenciales incorrectas para el usuario {}: {}", userDto.getName(), e.getMessage());
            throw new UserNotAuthorized("Credenciales incorrectas para el usuario " + userDto.getName() + ".");
        } catch (Exception e) {
            logger.error("Error inesperado durante el inicio de sesion para el usuario {}: {}", userDto.getName(), e.getMessage());
            throw new RuntimeException("Error inesperado durante el inicio de sesión");
        }
    }


    @Override
    public UserDto findUserById(Long id){
        System.out.println(id);
      User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("El usuario con el id: "
              + id + "no existe"));

        return new UserDto(user);
    }


    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll(); // Método que devuelve todos los usuarios
    }

}
