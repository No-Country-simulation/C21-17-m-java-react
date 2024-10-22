package com.microservice.user.microservice_user.services;
import com.microservice.user.microservice_user.entities.User;
import com.microservice.user.microservice_user.entities.Profile;
import com.microservice.user.microservice_user.repositories.UserProfileRepository;
import com.microservice.user.microservice_user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserProfileServiceImpl implements IUserProfileService{

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Profile save(Profile userProfile, Long userId) {

        User userWanted = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el ID: " + userId));

        // Asocia el UserProfile con el User
        userProfile.setUser(userWanted);

        // Establece el UserProfile dentro del User
        userWanted.setProfile(userProfile);

        try {
            // Guarda el usuario, tambien guardara el perfil debido al CASCADE.ALL)
            return userProfileRepository.save(userProfile);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar el perfil de usuario: " + e.getMessage(), e);
        }
    }

}
