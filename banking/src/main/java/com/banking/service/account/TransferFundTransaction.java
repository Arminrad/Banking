package com.banking.service.account;

import com.banking.dao.entities.dto.TransactionDto;
import com.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;

@Transactional(readOnly = true)
@Service
@Qualifier("transferFund")
public class TransferFundTransaction extends AccountTransaction implements TransactionPerformer {
    private final DepositTransaction depositTransaction;
    private final WithdrawTransaction withdrawTransaction;
    private final TransactionService transactionService;
    private final ExecutorService executorService;

    public TransferFundTransaction(@Qualifier("accountTransaction") AccountService accountService, DepositTransaction depositTransaction, WithdrawTransaction withdrawTransaction, TransactionService transactionService, ExecutorService executorService) {
        super(accountService);
        this.depositTransaction = depositTransaction;
        this.withdrawTransaction = withdrawTransaction;
        this.transactionService = transactionService;
        this.executorService = executorService;
    }

    @Transactional
    public synchronized boolean transferFunds(TransactionDto transactionDto) {
        executorService.submit(() -> {
            try {
                withdrawTransaction.withdraw(transactionDto);
                depositTransaction.deposit(transactionDto);
                transactionService.createTransaction(transactionDto);
            } catch (Exception e) {
                throw new RuntimeException();
            }
            notifyLogger(transactionDto);
        });
        return true;
    }

    @Transactional
    public synchronized void perform(TransactionDto transactionDto) {
        transferFunds(transactionDto);
    }
}
