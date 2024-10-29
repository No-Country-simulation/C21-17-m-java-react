package com.microservice.course.controllers;

import com.microservice.course.entities.Module;
import com.microservice.course.entities.Video;
import com.microservice.course.services.IVideoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course/video")
public class VideoController {

    @Autowired
    private IVideoService videoService;

    @PostMapping("/{id}")
    public ResponseEntity<?> create(@Valid @RequestBody Video video, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(videoService.save(video, id));
    }

    @DeleteMapping("/{moduleId}/{videoId}/{userId}") // Asegúrate de usar nombres descriptivos en la ruta
    public ResponseEntity<?> delete(@PathVariable Long moduleId, @PathVariable Long videoId, @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(videoService.deleteVideo(moduleId,videoId,userId));
    }

    @GetMapping()
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(200).body(videoService.findAll());
    }

    @GetMapping("/video/{videoId}")
    public ResponseEntity<?> findById(@PathVariable Long videoId){
        return ResponseEntity.ok(videoService.findById(videoId));
    }
}
