package com.example.banking_application.controller;

import com.example.banking_application.dtos.CustomerResponse;
import com.example.banking_application.dtos.LoginDto;
import com.example.banking_application.dtos.RegisterRequestDto;
import com.example.banking_application.dtos.converter.CustomerToCustomerResponse;
import com.example.banking_application.model.Customer;
import com.example.banking_application.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerToCustomerResponse customerMapper;


    @GetMapping
    public ResponseEntity<CustomerResponse> getCustomer(@RequestBody UUID customerId) {
        Customer customer = this.customerService.verifyCustomer(customerId);
        return new ResponseEntity<>(this.customerMapper.convert(customer), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody @Valid RegisterRequestDto customerInfo) {
        Customer customer = this.customerService.registerUser(customerInfo);
        return new ResponseEntity<>(this.customerMapper.convert(customer), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<UUID> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(this.customerService.loginCustomer(loginDto), HttpStatus.OK);
    }

}
