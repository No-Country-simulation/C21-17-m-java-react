package com.microservice.payment.microservice_payment.repository;

import com.microservice.payment.microservice_payment.entities.UserCourses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCourseRepository extends CrudRepository<UserCourses, Long> {
}
