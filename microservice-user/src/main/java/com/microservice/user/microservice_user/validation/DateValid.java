package com.microservice.user.microservice_user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = DateValidValidation.class) // Definimos la clase que valida nuestra anotacion personalizada
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateValid {

    String message() default "La edad minima permitida es de 12 años";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
