package com.microservice.user.microservice_user.exceptions;

public class UserNotAuthorized extends RuntimeException {
    public UserNotAuthorized(String message) {
        super(message);
    }
}