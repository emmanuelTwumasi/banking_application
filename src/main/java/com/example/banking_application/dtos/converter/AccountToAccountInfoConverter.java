package com.example.banking_application.dtos.converter;

import com.example.banking_application.dtos.AccountDto;
import com.example.banking_application.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Component
public class AccountToAccountInfoConverter implements Converter<Account, AccountDto> {
    @Override
    public AccountDto convert(Account source) {
        if (source==null){
            return null;
        }
        return new AccountDto(source.getId(),source.getAccount_type(),source.getCustomer().getUsername(),source.getCustomer().getFirstName(),
                source.getCustomer().getLastName(), source.getBalance(), source.getCreatedAt());
    }

    @Override
    public List<AccountDto> convert(List<Account> sourceList) {
        if (sourceList.isEmpty()){
            return Collections.emptyList();
        }
        List<AccountDto> accounts = new ArrayList<>();
        for (Account account:sourceList) {
            accounts.add(convert(account));
        }
        return accounts;
    }
}
