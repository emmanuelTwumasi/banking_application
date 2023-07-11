package com.example.banking_application.service;

import com.example.banking_application.dtos.LoginDto;
import com.example.banking_application.dtos.RegisterRequestDto;
import com.example.banking_application.model.Customer;

import java.util.UUID;

public interface CustomerService {
    Customer registerUser(RegisterRequestDto customerInfo);

    Customer verifyCustomer(UUID customerId);

    UUID loginCustomer(LoginDto loginDto);
}
