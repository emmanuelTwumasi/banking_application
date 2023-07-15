package com.example.banking_application.dtos;

import com.example.banking_application.model.enums.ACCOUNT_TYPE;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * A DTO for the {@link com.example.banking_application.model.Account} entity
 */
public record AccountDto(UUID id,
                         ACCOUNT_TYPE account_type,
                         String customerUsername,
                         String customerFirstName,
                         String customerLastName, double balance,
                         @JsonFormat(pattern = "dd MMMM yyyy, hh:mm a")
                         LocalDateTime createdAt) implements Serializable {
}