package com.microservice.course.exceptions;

public class UnauthorizedActionException extends RuntimeException{
    public UnauthorizedActionException() {
        super("El usuario no se encuentra autorizado para esta accion");
    }
}
