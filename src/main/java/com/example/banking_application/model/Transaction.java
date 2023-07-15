package com.example.banking_application.model;

import com.example.banking_application.model.enums.TRANSACTION_TYPE;
import com.example.banking_application.model.enums.TRANSACTION_STATUS;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Transaction extends AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private String description;

    @Enumerated(EnumType.STRING)
    private TRANSACTION_STATUS status;
    @Enumerated(EnumType.STRING)
    private TRANSACTION_TYPE type;

    private double initialBalance;

    private double amount;

    private double currentBalance;

    private String sender;

    private String recipient;


}

