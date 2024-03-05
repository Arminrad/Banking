package com.banking.mapper;

import com.banking.dao.entities.Transaction;
import com.banking.dao.entities.Transaction.TransactionBuilder;
import com.banking.dao.entities.dto.TransactionDto;
import com.banking.dao.entities.dto.TransactionDto.TransactionDtoBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-05T11:42:35+0330",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.10 (Amazon.com Inc.)"
)
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction transactionDtoToTransaction(TransactionDto dto) {
        if ( dto == null ) {
            return null;
        }

        TransactionBuilder transaction = Transaction.builder();

        transaction.transactionDate( dto.getTransactionDate() );
        transaction.transactionType( dto.getTransactionType() );
        transaction.transactionAmount( dto.getTransactionAmount() );
        transaction.account( dto.getAccount() );

        return transaction.build();
    }

    @Override
    public TransactionDto transactionToTransactionDto(Transaction entity) {
        if ( entity == null ) {
            return null;
        }

        TransactionDtoBuilder transactionDto = TransactionDto.builder();

        transactionDto.transactionDate( entity.getTransactionDate() );
        transactionDto.transactionType( entity.getTransactionType() );
        transactionDto.transactionAmount( entity.getTransactionAmount() );
        transactionDto.account( entity.getAccount() );

        return transactionDto.build();
    }
}
