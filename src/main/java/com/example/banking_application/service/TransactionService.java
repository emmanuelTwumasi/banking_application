package com.example.banking_application.service;

import com.example.banking_application.model.Account;
import com.example.banking_application.model.Transaction;
import com.example.banking_application.model.enums.TRANSACTION_STATUS;
import com.example.banking_application.model.enums.TRANSACTION_TYPE;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    Transaction addTransaction(Transaction transaction);

    List<Transaction> getAccountTransactionHistory(UUID account);


    Transaction getTransaction(UUID transactionId);

    void performTransaction(double amount, Account account,
                            double initialAmount, TRANSACTION_STATUS transactionStatus,
                            Transaction transaction, TRANSACTION_TYPE transactionType);
}
