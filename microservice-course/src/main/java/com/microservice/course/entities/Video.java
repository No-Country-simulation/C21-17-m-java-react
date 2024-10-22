package com.microservice.course.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "video", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"url"}), // Asegura que la URL sea única
        @UniqueConstraint(columnNames = {"moduleId"}) // Asegura que el moduleId sea único
})
@Data
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del módulo no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @NotNull(message = "El título no puede ser nulo")
    @Size(min = 4, max = 100, message = "El título debe tener entre 4 y 100 caracteres")
    private String title;

    @NotNull(message = "La URL no puede ser nula")
    @Size(max = 255, message = "La URL no puede exceder los 255 caracteres")
    @Pattern(regexp = "^(http|https)://.*$", message = "La URL debe ser válida y comenzar con http o https")
    private String url;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String description;
}
