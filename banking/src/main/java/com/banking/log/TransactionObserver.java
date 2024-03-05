package com.banking.log;

public interface TransactionObserver{
    void onTransaction(String accountNumber, String transactionType, double amount);
}
