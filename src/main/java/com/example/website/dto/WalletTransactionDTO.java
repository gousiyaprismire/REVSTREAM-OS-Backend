package com.example.website.dto;

import com.example.website.entity.WalletTransaction;
import com.example.website.enums.TransactionType;

import java.time.LocalDateTime;

public class WalletTransactionDTO {

    private Long id;
    private TransactionType type;          // ADD, TASK_LOCK, TASK_RELEASE, WITHDRAW
    private Double amount;
    private String status;        // SUCCESS, FAILED
    private LocalDateTime createdAt;

    private String direction;     // CREDIT or DEBIT
    private String description;   // Human readable
    private Long taskId;          // Only for task related txns
    private Long counterpartyId;  // Other user involved (if any)

    public WalletTransactionDTO(
            Long id,
            TransactionType type,
            Double amount,
            String status,
            LocalDateTime createdAt,
            String direction,
            String description,
            Long taskId,
            Long counterpartyId
    ) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.direction = direction;
        this.description = description;
        this.taskId = taskId;
        this.counterpartyId = counterpartyId;
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getDirection() {
        return direction;
    }

    public String getDescription() {
        return description;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Long getCounterpartyId() {
        return counterpartyId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setCounterpartyId(Long counterpartyId) {
        this.counterpartyId = counterpartyId;
    }
}

