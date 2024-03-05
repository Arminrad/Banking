package com.banking.dao.entities;

public enum TransactionType {
    WITHDRAWAL("withdrawal"),
    DEPOSIT("deposit"),
    TRANSFER("transfer");

    TransactionType(String input) {
    }
}
