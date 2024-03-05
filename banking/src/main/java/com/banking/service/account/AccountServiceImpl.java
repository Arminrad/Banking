package com.banking.service.account;

import com.banking.dao.AccountRepository;
import com.banking.dao.entities.Account;
import com.banking.dao.entities.TransactionType;
import com.banking.dao.entities.dto.AccountDto;
import com.banking.dao.entities.dto.TransactionDto;
import com.banking.exception.NoAccountFoundException;
import com.banking.log.TransactionLogger;
import com.banking.log.TransactionObserver;
import com.banking.mapper.AccountMapper;
import com.banking.service.TransactionServiceImpl;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@Transactional(readOnly = true)
@Service
@Qualifier("accountService")
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final TransactionLogger transactionLogger;
    private final AccountRepository accountRepository;
    private final ExecutorService executorService;
    private final TransactionServiceImpl transactionService;
    private final TransactionObserver transactionObserver;
    private final AccountMapper accountMapper;
    @Setter
    private TransactionPerformer transactionPerformer;

    public AccountServiceImpl(TransactionLogger transactionLogger, AccountRepository accountRepository, ExecutorService executorService, TransactionServiceImpl transactionService, TransactionObserver transactionObserver, AccountMapper accountMapper) {
        this.transactionLogger = transactionLogger;
        this.accountRepository = accountRepository;
        this.executorService = executorService;
        this.transactionService = transactionService;
        this.transactionObserver = transactionObserver;
        this.accountMapper = accountMapper;
    }

    @Transactional
    public Account createAccount(AccountDto accountDto) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> {
            Account account = accountMapper.accountDtoToAccount(accountDto);
            Account createdAccount = accountRepository.save(account);
            TransactionDto transactionDto = new TransactionDto(
                    null,
                    LocalDateTime.now(),
                    TransactionType.DEPOSIT.name(),
                    createdAccount.getBalance(),
                    createdAccount);
            transactionService.createTransaction(transactionDto);
            notifyLogger(transactionDto);
            return createdAccount;
        }).get();
    }

    public Account findAccountById(AccountDto accountDto) {
        return accountRepository.findById(accountDto.getId()).orElseThrow(() -> new RuntimeException(""));
    }

    public Account findAccountByName(String holderName) {
        Account account = accountRepository.findAccountByAccountHolderName(holderName);
        if (account == null) {
            throw new NoAccountFoundException();
        }
        return account;
    }

    public void notifyLogger(TransactionDto transactionDto) {
        transactionObserver.onTransaction(transactionDto.getAccount().getId().toString(), transactionDto.getTransactionType(), transactionDto.getTransactionAmount());
    }

    @Transactional
    public void perform(TransactionDto transactionDto) throws ExecutionException, InterruptedException {
        transactionPerformer.perform(transactionDto);
    }
}
