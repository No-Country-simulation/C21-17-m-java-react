package com.microservice.user.microservice_user.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.microservice.user.microservice_user.validation.ExistsUrl;
import com.microservice.user.microservice_user.validation.DateValid;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ExistsUrl // Bean Validation personalizada
    private String picture;

    // Validacion para el numero de celular (debe contener solo numeros, con o sin el símbolo +)
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "El numero de celular debe ser valido")
    private String cellphone;

    @DateValid // Bean Validation personalizada
    private LocalDate date_of_birth;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


}
