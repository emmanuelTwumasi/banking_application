package com.example.banking_application.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class AuditableEntity {

    @JsonFormat(pattern = "dd MMMM yyyy, hh:mm a")
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd MMMM yyyy, hh:mm a")
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
