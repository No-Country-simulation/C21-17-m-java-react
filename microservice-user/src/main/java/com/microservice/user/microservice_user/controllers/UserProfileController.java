package com.microservice.user.microservice_user.controllers;

import com.microservice.user.microservice_user.entities.Profile;
import com.microservice.user.microservice_user.services.IUserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users-profiles")
public class UserProfileController {

    @Autowired
    private IUserProfileService userProfileService;

    @PostMapping("/{idUser}")
    public ResponseEntity<?> create(@Valid @RequestBody Profile userProfile, @PathVariable Long idUser) { // Verificar, posee errores
        Profile createdUserProfile = userProfileService.save(userProfile, idUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserProfile);
    }
}
