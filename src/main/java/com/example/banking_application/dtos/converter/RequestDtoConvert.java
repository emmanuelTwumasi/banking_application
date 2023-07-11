package com.example.banking_application.dtos.converter;

import com.example.banking_application.dtos.RegisterRequestDto;
import com.example.banking_application.model.Customer;
import org.springframework.stereotype.Component;

import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RequestDtoConvert implements Converter<RegisterRequestDto, Customer>{
    @Override
    public Customer convert(RegisterRequestDto source) {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName(source.firstName());
        newCustomer.setLastName(source.lastName());
        newCustomer.setUsername(source.email());
        newCustomer.setPassword(source.password());
        newCustomer.setAge(source.age());
        return newCustomer;
    }

    @Override
    public List<Customer> convert(List<RegisterRequestDto> sourceList) {
        if (sourceList==null){
            return Collections.emptyList();
        }
        List<Customer> customerList  = new ArrayList<>();
        for (RegisterRequestDto customerInfo : sourceList) {
            customerList.add(convert(customerInfo));
        }
        return customerList;
    }
}
