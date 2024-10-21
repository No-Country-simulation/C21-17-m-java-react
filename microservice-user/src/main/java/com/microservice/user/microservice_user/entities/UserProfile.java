package com.microservice.user.microservice_user.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.microservice.user.microservice_user.validation.ExistsUrl;
import com.microservice.user.microservice_user.validation.DateValid;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    private Long id;

    @ExistsUrl // Bean Validation personalizada
    private String profile_picture;

    // Validacion para el numero de celular (debe contener solo numeros, con o sin el símbolo +)
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "El numero de celular debe ser valido")
    private String cellphone;

    @Size(min = 20, message = "La biografia debe tener minimo 20 caracteres")
    private String bio;

    @DateValid // Bean Validation personalizada
    private LocalDate date_of_birth;

    @OneToOne
    @MapsId // Establecemos como primary key Y foregin key 'id_user'
    @JsonBackReference
    @JoinColumn(name = "id_user")
    private User user;


}
