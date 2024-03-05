package com.banking.service.account;

import com.banking.dao.AccountRepository;
import com.banking.dao.entities.Account;
import com.banking.dao.entities.dto.TransactionDto;
import com.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@Transactional(readOnly = true)
@Service
@Qualifier("withdraw")
public class WithdrawTransaction extends AccountTransaction implements TransactionPerformer {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final ExecutorService executorService;

    public WithdrawTransaction(@Qualifier("accountTransaction") AccountService accountService, AccountRepository accountRepository, TransactionService transactionService, ExecutorService executorService) {
        super(accountService);
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
        this.executorService = executorService;
    }

    @Transactional
    public synchronized Account withdraw(TransactionDto transactionDto) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> {
            Account account = accountRepository.findById(transactionDto.getAccount().getId()).orElseThrow(() -> new RuntimeException("Account not found"));
            if (account.getBalance() < transactionDto.getTransactionAmount()) {
                throw new RuntimeException("Insufficient funds");
            }
            account.setBalance(account.getBalance() - transactionDto.getTransactionAmount());
            Account withdrewAccount = accountRepository.save(account);
            transactionService.createTransaction(transactionDto);
            return withdrewAccount;
        }).get();
    }

    @Transactional
    public synchronized void perform(TransactionDto transactionDto) throws ExecutionException, InterruptedException {
        withdraw(transactionDto);
        notifyLogger(transactionDto);
    }
}
