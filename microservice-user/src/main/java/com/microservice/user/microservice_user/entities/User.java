package com.microservice.user.microservice_user.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Data // Con la anotacion 'Data' de lombok los getters y setters se encuentran configurados pero ocultos
 // Construye un constructor vacio
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 80)
    private String name;


    @NotBlank
    @Size(min = 6, max = 80)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", message = "Debe contener al menos una letra y un número")
    private String password;

    @Email(message = "Formato de correo electronico invalido")
    @Column(unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    public User() {
        // Asignar un rol por defecto, por ejemplo, "Usuario"
        this.role = new Role("USER"); // Asegúrate de que "Usuario" exista en tu base de datos
    }
}
