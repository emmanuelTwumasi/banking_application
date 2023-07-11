package com.example.banking_application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank(message = "Please provide your email.") String email,
        @NotBlank(message = "Please provide your first name.") String firstName,
                               @NotBlank(message = "Please provide a password")  String password,
                                 @NotBlank(message = "Please provide your last name.") String lastName,
                                 @Size(min =18,  message = "Age must be 18yrs or more. ") int age,
                                 @NotBlank(message = "Please provide your date of birth.") String dateOfBirth
                        ) {
}
