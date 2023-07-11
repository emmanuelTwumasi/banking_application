package com.example.banking_application.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
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

    private float balance = 20;

    @CreatedDate
    private LocalDateTime createdAt;

}
