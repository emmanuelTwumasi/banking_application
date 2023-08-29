package com.example.banking_application.service;

import com.example.banking_application.dtos.LoginDto;
import com.example.banking_application.dtos.RegisterRequestDto;
import com.example.banking_application.dtos.converter.RequestDtoConvert;
import com.example.banking_application.model.Customer;
import com.example.banking_application.repository.CustomerRepository;
import com.example.banking_application.service.serviceImp.AccountServiceImp;
import com.example.banking_application.service.serviceImp.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RequestDtoConvert registerDtoMapper;

    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, registerDtoMapper);
    }

    @Test
    void registerUser() {
        LocalDate dateOfBirth = LocalDate.of(1990, 4, 3);
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setUsername("joe@gmail.com");
        customer.setPassword("joewus");
        customer.setLastName("Doe");
        customer.setFirstName("joe");
        customer.setDateOfBirth(dateOfBirth);

        RegisterRequestDto customerInfo = new RegisterRequestDto(
                customer.getUsername(),customer.getFirstName(),customer.getPassword(),
                customer.getLastName(),18,dateOfBirth);

        when(this.registerDtoMapper.convert(any(RegisterRequestDto.class))).thenReturn(customer);
        when(this.customerRepository.save(any())).thenReturn(customer);

        Customer registerUser = this.customerService.registerUser(customerInfo);
        assertEquals(registerUser.getUsername(),customerInfo.email());
    }

    @Test
    void verifyCustomer() {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setId(customerId);
        when(this.customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer customer1 = this.customerService.verifyCustomer(customerId);

        assertEquals(customer.getId(),customer1.getId());
    }

    @Test
    void loginCustomer_WithValidCustomerEmailAndPassword_ReturnCustomerId() {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setUsername("joe@gmail.com");
        customer.setPassword("joewus");

        when(this.customerRepository.findCustomerByUsernameAndPassword(customer.getUsername(),customer.getPassword()))
                .thenReturn(Optional.of(customer));

        UUID loginCustomer = this.customerService.loginCustomer(new LoginDto(customer.getUsername(),customer.getPassword()));

        assertEquals(loginCustomer,customerId);
    }
}