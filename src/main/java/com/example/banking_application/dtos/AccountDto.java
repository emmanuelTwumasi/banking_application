package com.example.banking_application.dtos;

import com.example.banking_application.model.ACCOUNT_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    ACCOUNT_TYPE account_type;
    RegisterRequestDto customerDetails;
}
