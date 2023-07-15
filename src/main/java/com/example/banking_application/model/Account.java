package com.example.banking_application.model;

import com.example.banking_application.model.enums.ACCOUNT_TYPE;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account extends AuditableEntity {
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

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (!hasSufficientBalance(amount)) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance -= amount;
    }

    public boolean hasSufficientBalance(double amount) {
        return balance >= amount;
    }


}
