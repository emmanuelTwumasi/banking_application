package com.example.banking_application.dtos.converter;

import com.example.banking_application.dtos.TransactionDto;
import com.example.banking_application.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class TransactionToTransactionResponseConverter implements Converter<Transaction, TransactionDto> {
    @Override
    public TransactionDto convert(Transaction source) {
        if (source == null) {
            return null;
        }

        return new TransactionDto(source.getId(), source.getAccount().getAccount_type(),
                source.getCreatedAt(), source.getDescription(), source.getStatus(),
                source.getType(), source.getInitialBalance(), source.getAmount(),
                source.getBalance(), source.getSender(), source.getRecipient(),
                source.getCreatedAt(), source.getUpdatedAt());
    }

    @Override
    public List<TransactionDto> convert(List<Transaction> sourceList) {
        if (sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        List<TransactionDto> transactions = new ArrayList<>();
        for (Transaction transaction : sourceList) {
            transactions.add(convert(transaction));
        }
        return transactions;
    }
}
