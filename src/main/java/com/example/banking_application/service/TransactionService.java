package com.example.banking_application.service;

import com.example.banking_application.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    Transaction addTransaction(Transaction transaction);

    List<Transaction> getTransactionsByAccount(UUID account);


    Transaction getTransaction(UUID transactionId);
}
