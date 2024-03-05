package com.banking.service.account;

import com.banking.dao.entities.dto.TransactionDto;

import java.util.concurrent.ExecutionException;

public interface TransactionPerformer {
    void perform(TransactionDto transactionDto) throws ExecutionException, InterruptedException;
}
