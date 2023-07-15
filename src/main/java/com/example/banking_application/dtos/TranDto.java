package com.example.banking_application.dtos;

import com.example.banking_application.model.enums.TRANSACTION_TYPE;

import java.math.BigDecimal;
import java.util.UUID;

public record TranDto(UUID senderId, UUID accountId, TRANSACTION_TYPE transactionType, BigDecimal amount,String description,String recipient) {
}
