package com.example.banking_application.exceptions;


public class AccountNotFoundException extends RuntimeException {
    String message;

    public AccountNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
