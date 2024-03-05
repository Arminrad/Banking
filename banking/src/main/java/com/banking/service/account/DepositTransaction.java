package com.banking.service.account;

import com.banking.dao.AccountRepository;
import com.banking.dao.entities.Account;
import com.banking.dao.entities.TransactionType;
import com.banking.dao.entities.dto.TransactionDto;
import com.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@Transactional(readOnly = true)
@Service
@Qualifier("deposit")
public class DepositTransaction extends AccountTransaction implements TransactionPerformer {

    private final AccountRepository accountRepository;
    private final ExecutorService executorService;
    private final TransactionService transactionService;
    public DepositTransaction(@Qualifier("accountTransaction") AccountService accountService, AccountRepository accountRepository, ExecutorService executorService, TransactionService transactionService) {
        super(accountService);
        this.accountRepository = accountRepository;
        this.executorService = executorService;
        this.transactionService = transactionService;
    }

    @Transactional
    public synchronized Account deposit(TransactionDto transactionDto) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> {
            Account account;
            if (transactionDto.getTransactionType().equals(TransactionType.TRANSFER.name())) {
                account = accountRepository.findById(transactionDto.getDestinationAccountNumber().getId()).orElseThrow(() -> new RuntimeException("Account not found"));
            } else {
                account = accountRepository.findById(transactionDto.getAccount().getId()).orElseThrow(() -> new RuntimeException("Account not found"));
            }
            account.setBalance(account.getBalance() + transactionDto.getTransactionAmount());
            Account depositedAccount = accountRepository.save(account);
            transactionService.createTransaction(transactionDto);
            return depositedAccount;
        }).get();
    }

    @Transactional
    public synchronized void perform(TransactionDto transactionDto) throws ExecutionException, InterruptedException {
        deposit(transactionDto);
        notifyLogger(transactionDto);
    }
}
