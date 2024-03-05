package com.banking.service.account;

import com.banking.dao.entities.Account;
import com.banking.dao.entities.dto.AccountDto;
import com.banking.dao.entities.dto.TransactionDto;

import java.util.concurrent.ExecutionException;

public interface AccountService {

    Account createAccount(AccountDto accountDto) throws ExecutionException, InterruptedException;
    Account findAccountById(AccountDto accountDto);
    Account findAccountByName(String holderName);
    void notifyLogger(TransactionDto transactionDto);
}
