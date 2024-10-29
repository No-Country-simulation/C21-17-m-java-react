package com.microservice.course.client;

import com.microservice.course.controllers.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "msvc-user", url = "localhost:9030") // Agregar http://
public interface UserClient {

    @GetMapping("/api/v1/users/{id}")
    UserDTO findUserById(@PathVariable Long id);

}
