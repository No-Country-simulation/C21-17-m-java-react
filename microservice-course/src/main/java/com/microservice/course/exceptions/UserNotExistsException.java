package com.microservice.course.exceptions;

public class UserNotExistsException extends RuntimeException{
    public UserNotExistsException() {
        super("No existe el id del usuario en sistema");
    }
}
