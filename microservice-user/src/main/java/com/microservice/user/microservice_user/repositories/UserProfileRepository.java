package com.microservice.user.microservice_user.repositories;

import com.microservice.user.microservice_user.entities.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {

}
