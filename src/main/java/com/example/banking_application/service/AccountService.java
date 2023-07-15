package com.example.banking_application.service;

import com.example.banking_application.dtos.AccountDto;
import com.example.banking_application.model.Account;
import com.example.banking_application.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    Account createAccount(AccountDto accountInfo);
    Account getAccount(UUID customerId,UUID accountId) ;

    Account getAccount(UUID accountId);

    Account updateAccount(Account account);
    void deposit(UUID accountNumber, double amount);
    void withdraw(UUID accountNumber, double amount);
    double getAccountBalance(UUID accountNumber);
    List<Transaction> getTransactionHistory(UUID accountNumber);
}
