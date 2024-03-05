package com.banking.service;

import com.banking.dao.TransactionRepository;
import com.banking.dao.entities.Transaction;
import com.banking.dao.entities.dto.TransactionDto;
import com.banking.mapper.TransactionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private TransactionMapper transactionMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Transactional
    public Transaction createTransaction(TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.transactionDtoToTransaction(transactionDto);
        return transactionRepository.save(transaction);
    }
}
