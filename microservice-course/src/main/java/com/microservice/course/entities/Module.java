package com.microservice.course.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "module", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"courseId", "title"}) // Un modulo es unico y pertenece a un solo curso
})
@Data
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del curso no puede ser nulo")
    @ManyToOne // Indica que muchos módulos pueden pertenecer a un curso
    @JoinColumn(name = "course_id", nullable = false) // Define la columna que hace referencia a la tabla de cursos
    private Course course; // Cambiar a Course para la relación

    @NotNull(message = "El título no puede ser nulo")
    @Size(min = 4, max = 100, message = "El título debe tener entre 4 y 100 caracteres")
    private String title;

    @Size(min = 15, max = 255, message = "La descripción debe tener entre 15 y 255 caracteres")
    private String description;

}
