package com.banking.service.account;

import com.banking.dao.entities.Account;
import com.banking.dao.entities.dto.AccountDto;
import com.banking.dao.entities.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

@Transactional(readOnly = true)
@Service
@Qualifier("accountTransaction")
public class AccountTransaction implements AccountService {

    private final AccountService accountService;

    public AccountTransaction(@Qualifier("accountService") AccountService accountService) {
        this.accountService = accountService;
    }

    @Transactional
    @Override
    public Account createAccount(AccountDto accountDto) {
        try {
            return accountService.createAccount(accountDto);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findAccountById(AccountDto accountDto) {
        return accountService.findAccountById(accountDto);
    }

    @Override
    public Account findAccountByName(String holderName) {
        return accountService.findAccountByName(holderName);
    }

    @Override
    public void notifyLogger(TransactionDto transactionDto) {
        accountService.notifyLogger(transactionDto);
    }
}
