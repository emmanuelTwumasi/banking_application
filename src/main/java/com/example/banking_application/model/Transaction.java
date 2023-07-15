package com.example.banking_application.model;

import com.example.banking_application.model.enums.TRANSACTION_TYPE;
import com.example.banking_application.model.enums.TRANSACTION_STATUS;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @CreatedDate
    private LocalDateTime timestamp;

    private String description;

    private TRANSACTION_STATUS status;
    private TRANSACTION_TYPE type;

//    private String paymentMethod;

    private double initialBalance;

    private double amount;

    private double currentBalance;

    private String sender;

    private String recipient;

}
