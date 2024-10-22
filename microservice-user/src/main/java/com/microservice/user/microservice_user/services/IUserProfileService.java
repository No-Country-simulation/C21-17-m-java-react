package com.microservice.user.microservice_user.services;

import com.microservice.user.microservice_user.entities.Profile;

public interface IUserProfileService {

    Profile save(Profile user, Long userId);
}
