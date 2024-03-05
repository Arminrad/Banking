package com.banking.applicationcontext;

import com.banking.mapper.AccountMapper;
import com.banking.mapper.TransactionMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class Config {

    @Bean
    public TransactionMapper getTransactionMapper() {
        return Mappers.getMapper(TransactionMapper.class);
    }

    @Bean
    public AccountMapper getAccountMapper() {
        return Mappers.getMapper(AccountMapper.class);
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(100);
    }
}
