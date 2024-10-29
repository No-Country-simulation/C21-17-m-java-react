package com.microservice.course.services;

import com.microservice.course.entities.Video;
import com.microservice.course.http.response.CourseByModuleResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IVideoService {

    List<Video> findAll();

    Map<String, String> save(Video video, Long id);

    ResponseEntity<Video> findById(Long idVideo);

    Map<String, String> deleteVideo(Long moduleId, Long videoId, Long userId);
}
