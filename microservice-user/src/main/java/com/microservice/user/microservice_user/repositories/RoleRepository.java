package com.microservice.user.microservice_user.repositories;

import com.microservice.user.microservice_user.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
