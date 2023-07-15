package com.example.banking_application.dtos;

import java.util.UUID;

public record AccountRequest(UUID customerId,
                             UUID accountId) {
}
