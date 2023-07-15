package com.example.banking_application.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record RegisterRequestDto(
        @NotBlank(message = "Please provide your email.") String email,
        @NotBlank(message = "Please provide your first name.") String firstName,
        @NotBlank(message = "Please provide a password") String password,
        @NotBlank(message = "Please provide your last name.") String lastName,
        @Min(value = 18, message = "Age must be at least 18") int age,
        LocalDate dateOfBirth
) {
}
