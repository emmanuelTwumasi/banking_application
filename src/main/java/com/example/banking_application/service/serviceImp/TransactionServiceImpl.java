package com.example.banking_application.service.serviceImp;

import com.example.banking_application.model.Transaction;
import com.example.banking_application.repository.TransactionRepository;
import com.example.banking_application.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction addTransaction(Transaction transaction) {
        return this.transactionRepository.save(transaction);
    }
    @Override
    public List<Transaction> getTransactionsByAccount(UUID accountId) {
        return transactionRepository.getAllTransactionByAccountId(accountId);
    }
    @Override
    public Transaction getTransaction(UUID transactionId){
        return transactionRepository.findById(transactionId)
                .orElseThrow(()->new RuntimeException("Transaction not found with id: "+transactionId));
    }


}
