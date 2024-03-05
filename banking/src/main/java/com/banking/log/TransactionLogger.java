package com.banking.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TransactionLogger implements TransactionObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLogger.class);

    @Override
    public void onTransaction(String accountNumber, String transactionType, double amount) {
        LOGGER.info("Account number: " + accountNumber + "; Transaction type: " + transactionType + "; Amount: " + amount + "$");
    }
}
