package com.example.banking_application.dtos;

import java.util.UUID;


public record TransactionRequestDto(
        UUID accountId,
        double amount) {
}
