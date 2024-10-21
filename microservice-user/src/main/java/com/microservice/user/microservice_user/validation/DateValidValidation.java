package com.microservice.user.microservice_user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class DateValidValidation implements ConstraintValidator<DateValid, LocalDate> {

    @Override
    public boolean isValid(LocalDate dateOfBirthString, ConstraintValidatorContext context) {
        if (dateOfBirthString == null) {
            return true; // o false, dependiendo de si quieres que sea obligatorio
        }

        int age = Period.between(dateOfBirthString, LocalDate.now()).getYears();
        if (age < 12) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Debes tener al menos 12 años.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}



