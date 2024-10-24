package com.microservice.course.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@Entity
@Table(name = "course")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El título no puede ser nulo")
    @Size(min = 4, max = 100, message = "El título debe tener entre 4 y 100 caracteres")
    private String title;

    @NotNull(message = "La descripción no puede ser nula")
    @Size(min = 15, max = 255, message = "La descripción debe tener entre 15 y 255 caracteres")
    private String description;

    @NotNull(message = "El estado no puede ser nulo")
    private Boolean status;

    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Digits(integer = 6, fraction = 2, message = "El precio debe tener como máximo 6 dígitos enteros y 2 decimales")
    private Float price;


}
