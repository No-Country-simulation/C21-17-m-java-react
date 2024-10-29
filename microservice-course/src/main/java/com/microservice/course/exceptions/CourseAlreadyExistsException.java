package com.microservice.course.exceptions;

public class CourseAlreadyExistsException extends RuntimeException{
    public CourseAlreadyExistsException() {
        super("El curso ya existe");
    }
}
