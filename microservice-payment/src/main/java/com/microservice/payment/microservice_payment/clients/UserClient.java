package com.microservice.payment.microservice_payment.clients;

import com.microservice.payment.microservice_payment.controllers.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-user", url = "localhost:8080") // A que microservicio consultamos? AL GATEWAY SIEMPRE !
public interface UserClient {

    @GetMapping("/api/v1/users/search-by-course/{courseId}")
    List<UserDTO> findAllUserByCourse(@PathVariable Long courseId);
}
