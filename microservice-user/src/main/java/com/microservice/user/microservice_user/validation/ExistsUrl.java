package com.microservice.user.microservice_user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsUrlValidation.class) // Definimos la clase que valida nuestra anotacion personalizada
@Target(ElementType.FIELD) // Definimos el alcanze, podriamos incluir 'method' para que aplique a los methodos o parametros
@Retention(RetentionPolicy.RUNTIME) //  La anotación se retiene en el bytecode y está disponible para su uso durante la ejecución del programa. Esto permite que otros componentes de la aplicación (como marcos de trabajo o bibliotecas) lean la anotación y actúen en consecuencia.
public @interface ExistsUrl {
    String message() default "La URL no es valida o no se puede acceder";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
