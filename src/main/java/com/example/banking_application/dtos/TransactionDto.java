package com.example.banking_application.dtos;

import com.example.banking_application.model.enums.ACCOUNT_TYPE;
import com.example.banking_application.model.enums.TRANSACTION_STATUS;
import com.example.banking_application.model.enums.TRANSACTION_TYPE;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * A DTO for the {@link com.example.banking_application.model.Transaction} entity
 */
public record TransactionDto(UUID id,
                             ACCOUNT_TYPE accountAccount_type, LocalDateTime timestamp, String description,
                             TRANSACTION_STATUS status, TRANSACTION_TYPE type, double initialBalance, double amount,
                             double currentBalance, String sender, String recipient,
                             @JsonFormat(pattern = "dd MMMM yyyy, hh:mm a")
                             LocalDateTime createdAt,
                             @JsonFormat(pattern = "dd MMMM yyyy, hh:mm a")
                             LocalDateTime updatedAt) implements Serializable {
}