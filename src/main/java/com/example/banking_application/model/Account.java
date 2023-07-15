package com.example.banking_application.model;

import com.example.banking_application.model.enums.ACCOUNT_TYPE;
import com.example.banking_application.model.enums.TRANSACTION_TYPE;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ACCOUNT_TYPE account_type;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactionHistory;

    private double balance;

    @CreatedDate
    private LocalDateTime createdAt;
    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount> balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance -= amount;
    }
    public boolean hasSufficientBalance(double amount) {
        return balance >= amount;
    }


}
