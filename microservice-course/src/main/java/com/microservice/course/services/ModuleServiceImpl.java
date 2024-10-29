package com.microservice.course.services;

import com.microservice.course.client.UserClientImpl;
import com.microservice.course.controllers.dto.CourseDTO;
import com.microservice.course.controllers.dto.UserDTO;
import com.microservice.course.entities.Course;
import com.microservice.course.entities.Module;
import com.microservice.course.exceptions.CourseAlreadyExistsException;
import com.microservice.course.exceptions.CourseNotFoundException;
import com.microservice.course.exceptions.ModuleNotFoundException;
import com.microservice.course.exceptions.UserNotExistsException;
import com.microservice.course.http.response.CourseByModuleResponse;
import com.microservice.course.repositories.CourseRepository;
import com.microservice.course.repositories.ModuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ModuleServiceImpl implements IModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserClientImpl userClient;

    @Autowired
    private AuthorizationService authorizationService;

    private final Logger logger = LoggerFactory.getLogger(ModuleServiceImpl.class);

    @Transactional(readOnly = true)
    @Override
    public List<Module> findAll() {
        return (List<Module>) moduleRepository.findAll();
    }

    @Transactional
    @Override
    public Map<String, String> save(Module module, Long id) {
        try {

            UserDTO user = userClient.getUserById(id);
            authorizationService.checkUserRole(id, "ROLE_ADMIN");

            Optional<Course> course = Optional.ofNullable(courseRepository.findById(module.getCourse().getId())
                    .orElseThrow(CourseNotFoundException::new));

            Optional<Module> existingModule = moduleRepository.findByTitle(module.getTitle());
            if (existingModule.isPresent()) {
                logger.error("Intento de creacion fallido: el modulo {} ya existe.", module.getTitle());
                throw new CourseAlreadyExistsException();
            }

            moduleRepository.save(module);
            logger.info("Modulo {} agregado exitosamente.", module.getTitle());

            Map<String, String> messageResponse = new HashMap<>();
            messageResponse.put("Estado", "Modulo agregado exitosamente");
            messageResponse.put("Horario de registro:", LocalDateTime.now().toString());

            return messageResponse;
        } catch (Exception e) {
            logger.error("Error al registrar el curso: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
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

    @Transactional
    @Override
    public Map<String, String> deleteModule(Long courseId, Long moduleId, Long userId) {
        try {
            // Verificar la existencia del usuario y sus permisos
            UserDTO user = userClient.getUserById(userId);
            if (user == null) {
                throw new UserNotExistsException();
            }

            authorizationService.checkUserRole(userId, "ROLE_ADMIN");

            // Verificar la existencia del curso y módulo
            if (!courseRepository.existsById(courseId)) {
                logger.error("Intento de eliminacion fallido: el curso con ID {} no existe.", courseId);
                throw new CourseNotFoundException();
            }

            if (!moduleRepository.existsById(moduleId)) {
                logger.error("Intento de eliminacion fallido: el modulo con ID {} no existe.", moduleId);
                throw new ModuleNotFoundException();
            }

            // Eliminar el módulo
            moduleRepository.deleteById(moduleId);
            logger.info("Módulo con ID {} eliminado exitosamente por el usuario {}.", moduleId, user.getName());

            // Crear y devolver la respuesta
            Map<String, String> response = new HashMap<>();
            response.put("Estado", "Módulo eliminado exitosamente");
            response.put("Horario de eliminación", LocalDateTime.now().toString());

            return response;
        } catch (UserNotExistsException | CourseNotFoundException | ModuleNotFoundException e) {
            logger.error("Error al eliminar el módulo: {}", e.getMessage());
            throw e; // Rethrow para ser manejado en el controlador
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar el módulo con ID {}: {}", moduleId, e.getMessage());
            throw new RuntimeException("Error al intentar eliminar el módulo", e);
        }
    }

}
