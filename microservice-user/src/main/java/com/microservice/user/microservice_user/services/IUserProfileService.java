package com.microservice.user.microservice_user.services;

import com.microservice.user.microservice_user.entities.UserProfile;

public interface IUserProfileService {

    UserProfile save(UserProfile user, Long userId);
}
