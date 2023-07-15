package com.example.banking_application.service.serviceImp;

import com.example.banking_application.dtos.LoginDto;
import com.example.banking_application.dtos.RegisterRequestDto;
import com.example.banking_application.dtos.converter.RequestDtoConvert;
import com.example.banking_application.exceptions.CustomerNotFoundException;
import com.example.banking_application.model.Customer;
import com.example.banking_application.repository.CustomerRepository;
import com.example.banking_application.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;
    private final RequestDtoConvert registerDtoMapper;

    @Override
    public Customer registerUser(RegisterRequestDto customerInfo) {
        logger.debug("Registering a new customer.");
        Customer customer = this.registerDtoMapper.convert(customerInfo);

        Customer newCustomer = customerRepository.save(customer);

        logger.info("Customer registered successfully. Customer ID: {}", newCustomer.getId());
        return newCustomer;
    }

    @Override
    public Customer verifyCustomer(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer with found with id: " + customerId));
    }

    @Override
    public UUID loginCustomer(LoginDto loginDto) {
        logger.debug("Customer logging in.");
        Customer customer = this.customerRepository.
                findCustomerByUsernameAndPassword(loginDto.email(), loginDto.password())
                .orElseThrow(() -> new CustomerNotFoundException("Customer does not exist."));
        logger.info("Customer logged in successfully.");
        return customer.getId();
    }


}
