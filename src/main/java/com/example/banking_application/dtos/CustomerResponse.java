package com.example.banking_application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private UUID customerId;
    private String firstName;
    private String lastName;
    private int age;
    private LocalDate dateOfBirth;

}
