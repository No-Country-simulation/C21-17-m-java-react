package com.microservice.user.microservice_user.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import jakarta.persistence.Id;

@Data // Con la anotacion 'Data' de lombok los getters y setters se encuentran configurados pero ocultos
 // Construye un constructor vacio
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 80)
    private String name;


    @NotBlank
    @Size(min = 6, max = 80)
    private String password;

    @Email(message = "Formato de correo electronico invalido")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @OneToOne(mappedBy = "user") // Asegúrate de establecer la relación
    private Profile profile;


}
