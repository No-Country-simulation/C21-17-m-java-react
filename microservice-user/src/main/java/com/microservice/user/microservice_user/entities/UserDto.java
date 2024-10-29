package com.microservice.user.microservice_user.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    public UserDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.roleId = user.getRole();
    }

    private String name;
    private String email;
    private String password;
    private Role roleId;
}
