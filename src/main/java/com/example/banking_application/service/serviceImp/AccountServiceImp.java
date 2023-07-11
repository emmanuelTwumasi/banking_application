package com.example.banking_application.service.serviceImp;

import com.example.banking_application.dtos.AccountDto;
import com.example.banking_application.exceptions.AccountNotFoundException;
import com.example.banking_application.model.Account;
import com.example.banking_application.model.Customer;
import com.example.banking_application.repository.AccountRepository;
import com.example.banking_application.service.AccountService;
import com.example.banking_application.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountServiceImp implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    @Override
    public Account createAccount(AccountDto accountInfo){
        Customer customer = this.customerService.registerUser(accountInfo.getCustomerDetails());
        Account newAccount = new Account();
        newAccount.setAccount_type(accountInfo.getAccount_type());
        newAccount.setCustomer(customer);
        newAccount.setBalance(0);
        return accountRepository.save(newAccount);
    }

    @Override
    public Account getAccount(UUID customerId, UUID accountId) throws AuthenticationException {
            customerService.verifyCustomer(customerId);
        var account =  accountRepository.findById(accountId).orElseThrow(()-> new AccountNotFoundException("Account not found with number :"+accountId));
        if (customerId!= account.getCustomer().getId()){
            throw new AuthenticationException("Can't access this account");
        }
        return  account;
    }

}
