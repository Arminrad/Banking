package com.banking.service;

import com.banking.dao.entities.Transaction;
import com.banking.dao.entities.dto.TransactionDto;

public interface TransactionService {

    Transaction createTransaction(TransactionDto transactionDto);
}
