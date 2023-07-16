package com.example.banking_application.exceptions;


public class ResourceNotFoundException extends RuntimeException {
    String message;

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
