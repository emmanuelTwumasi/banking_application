package com.example.banking_application.service.serviceImp;

import com.example.banking_application.model.Account;
import com.example.banking_application.model.Transaction;
import com.example.banking_application.model.enums.TRANSACTION_STATUS;
import com.example.banking_application.model.enums.TRANSACTION_TYPE;
import com.example.banking_application.repository.TransactionRepository;
import com.example.banking_application.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    @Override
    public Transaction addTransaction(Transaction transaction) {
        logger.debug("Creating a new transaction.");
        Transaction newTransaction = this.transactionRepository.save(transaction);
        logger.info("Transaction created successfully.");
        return newTransaction;
    }
    @Override
    public List<Transaction> getAccountTransactionHistory(UUID accountId) {
        logger.debug("Retrieving transaction history on account with ID : "+accountId);
        List<Transaction> transactions = transactionRepository.getAllTransactionByAccountId(accountId);
        logger.info("Account transactions retrieved successfully.");
        return transactions;
    }
    @Override
    public Transaction getTransaction(UUID transactionId){
        return transactionRepository.findById(transactionId)
                .orElseThrow(()->new RuntimeException("Transaction not found with id: "+transactionId));
    }


    @Override
    public void performTransaction(double amount, Account account,
                                   double initialAmount, TRANSACTION_STATUS transactionStatus,
                                   Transaction transaction, TRANSACTION_TYPE transactionType) {
        transaction.setType(transactionType);
        transaction.setInitialBalance(initialAmount);
        transaction.setAmount(amount);
        transaction.setCurrentBalance(account.getBalance());
        transaction.setStatus(transactionStatus);
        transaction.setAccount(account);
        this.addTransaction(transaction);
    }

}
