package com.example.banking_application.repository;

import com.example.banking_application.model.Account;
import com.example.banking_application.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> getAllTransactionByAccountId(UUID account_id);
}