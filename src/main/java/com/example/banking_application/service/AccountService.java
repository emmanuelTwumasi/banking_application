package com.example.banking_application.service;

import com.example.banking_application.dtos.AccountRegistrationInfo;
import com.example.banking_application.model.Account;
import com.example.banking_application.model.enums.ACCOUNT_STATUS;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    Account createAccount(AccountRegistrationInfo accountInfo);

    Account changeAccountStatus(UUID accountId, ACCOUNT_STATUS status);

    List<Account> getCustomerAccounts(UUID customerId);

    Account getAccount(UUID customerId, UUID accountId);

    Account getAccount(UUID accountId);

    void deposit(UUID accountNumber, double amount);

    void withdraw(UUID accountNumber, double amount);

    double getAccountBalance(UUID accountNumber);
}
