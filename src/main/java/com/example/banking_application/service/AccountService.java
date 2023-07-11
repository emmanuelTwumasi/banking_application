package com.example.banking_application.service;

import com.example.banking_application.dtos.AccountDto;
import com.example.banking_application.model.Account;
import org.apache.tomcat.websocket.AuthenticationException;

import java.util.UUID;

public interface AccountService {
    Account createAccount(AccountDto accountInfo);
    Account getAccount(UUID customerId,UUID accountId) throws AuthenticationException;
}
