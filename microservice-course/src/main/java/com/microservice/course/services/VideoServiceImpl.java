package com.microservice.course.services;

import com.microservice.course.client.UserClientImpl;
import com.microservice.course.controllers.dto.CourseDTO;
import com.microservice.course.controllers.dto.UserDTO;
import com.microservice.course.entities.Course;
import com.microservice.course.entities.Module;
import com.microservice.course.entities.Video;
import com.microservice.course.exceptions.CourseAlreadyExistsException;
import com.microservice.course.exceptions.CourseNotFoundException;
import com.microservice.course.exceptions.ModuleNotFoundException;
import com.microservice.course.exceptions.UserNotExistsException;
import com.microservice.course.http.response.CourseByModuleResponse;
import com.microservice.course.repositories.ModuleRepository;
import com.microservice.course.repositories.VideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VideoServiceImpl implements IVideoService{

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private UserClientImpl userClient;

    private final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Override
    public List<Video> findAll() {
        return (List<Video>) videoRepository.findAll();
    }

    @Override
    public Map<String, String> save(Video video, Long id) {
        try {

            UserDTO user = userClient.getUserById(id);
            authorizationService.checkUserRole(id, "ROLE_ADMIN");

            Optional<Module> module = Optional.ofNullable(moduleRepository.findById(video.getModule().getId())
                    .orElseThrow(CourseNotFoundException::new));

            Optional<Video> existingModule = videoRepository.findByTitle(video.getTitle());
            if (existingModule.isPresent()) {
                logger.error("Intento de creacion fallido: el video {} ya existe.", video.getTitle());
                throw new CourseAlreadyExistsException();
            }

            videoRepository.save(video);
            logger.info("Video {} agregado exitosamente.", video.getTitle());

            Map<String, String> messageResponse = new HashMap<>();
            messageResponse.put("Estado", "Video agregado exitosamente");
            messageResponse.put("Horario de registro:", LocalDateTime.now().toString());

            return messageResponse;
        } catch (Exception e) {
            logger.error("Error al registrar el video: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity<Video> findById(Long idVideo) {
        Optional<Video> optionalVideo = videoRepository.findById(idVideo);

        // Retorna 404 Not Found si no existe el video
        return optionalVideo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Override
    public Map<String, String> deleteVideo(Long moduleId, Long videoId, Long userId) {
        try {
            // Verificar la existencia del usuario y sus permisos
            UserDTO user = userClient.getUserById(userId);
            if (user == null) {
                throw new UserNotExistsException();
            }

            authorizationService.checkUserRole(userId, "ROLE_ADMIN");

            // Verificar la existencia del curso y módulo
            if (!moduleRepository.existsById(moduleId)) {
                logger.error("Intento de eliminacion fallido: el modulo con ID {} no existe.", moduleId);
                throw new CourseNotFoundException();
            }

            if (!videoRepository.existsById(videoId)) {
                logger.error("Intento de eliminacion fallido: el video con ID {} no existe.", videoId);
                throw new ModuleNotFoundException();
            }

            // Eliminar el módulo
            videoRepository.deleteById(videoId);
            logger.info("Módulo con ID {} eliminado exitosamente por el usuario {}.", videoId, user.getName());

            // Crear y devolver la respuesta
            Map<String, String> response = new HashMap<>();
            response.put("Estado", "Video eliminado exitosamente");
            response.put("Horario de eliminación", LocalDateTime.now().toString());

            return response;
        } catch (UserNotExistsException | CourseNotFoundException | ModuleNotFoundException e) {
            logger.error("Error al eliminar el Video: {}", e.getMessage());
            throw e; // Rethrow para ser manejado en el controlador
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar el Video con ID {}: {}", videoId, e.getMessage());
            throw new RuntimeException("Error al intentar eliminar el Video", e);
        }
    }
}
