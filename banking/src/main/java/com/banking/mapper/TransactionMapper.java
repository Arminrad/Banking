package com.banking.mapper;

import com.banking.dao.entities.Transaction;
import com.banking.dao.entities.dto.TransactionDto;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionMapper {
    Transaction transactionDtoToTransaction(TransactionDto dto);
    TransactionDto transactionToTransactionDto(Transaction entity);
}