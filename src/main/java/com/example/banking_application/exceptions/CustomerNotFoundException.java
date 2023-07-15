package com.example.banking_application.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    String message;

    public CustomerNotFoundException(String s) {
        super(s);
        this.message = s;
    }
}
