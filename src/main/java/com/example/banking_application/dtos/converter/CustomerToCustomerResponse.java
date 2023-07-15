package com.example.banking_application.dtos.converter;


import com.example.banking_application.dtos.CustomerResponse;
import com.example.banking_application.model.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CustomerToCustomerResponse implements Converter<Customer, CustomerResponse> {
    @Override
    public CustomerResponse convert(Customer customer) {
        return new CustomerResponse(customer.getId(),
        customer.getFirstName(), customer.getLastName(), customer.getAge(), customer.getDateOfBirth());
    }

    @Override
    public List<CustomerResponse> convert(List<Customer> sourceList) {
        if (sourceList==null){
            return Collections.emptyList();
        }

        List<CustomerResponse> customerResponseList = new ArrayList<>();

        for (Customer customer :sourceList) {

            customerResponseList.add(convert(customer));
        }

        return customerResponseList;
    }
}
