package com.example.banking_application.dtos;

import com.example.banking_application.model.enums.ACCOUNT_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRegistrationInfo {
    ACCOUNT_TYPE account_type;
    UUID customerId;
}
