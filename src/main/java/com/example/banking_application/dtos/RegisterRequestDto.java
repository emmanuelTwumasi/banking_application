package com.example.banking_application.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record RegisterRequestDto(
        @NotBlank(message = "Please provide your email.") String email,
        @NotBlank(message = "Please provide your first name.") String firstName,
                               @NotBlank(message = "Please provide a password")  String password,
                                 @NotBlank(message = "Please provide your last name.") String lastName,
                                 @Size(min =18,  message = "Age must be 18yrs or more. ") int age,
                                 @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateOfBirth
                        ) {
}
