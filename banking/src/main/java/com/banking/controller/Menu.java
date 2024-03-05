package com.banking.controller;

import com.banking.applicationcontext.ApplicationContextProvider;
import com.banking.dao.entities.Account;
import com.banking.dao.entities.Bank;
import com.banking.dao.entities.TransactionType;
import com.banking.dao.entities.dto.AccountDto;
import com.banking.dao.entities.dto.TransactionDto;
import com.banking.exception.NoAccountFoundException;
import com.banking.exception.NoBankFoundException;
import com.banking.service.BankServiceImpl;
import com.banking.service.account.AccountServiceImpl;
import com.banking.service.account.DepositTransaction;
import com.banking.service.account.TransferFundTransaction;
import com.banking.service.account.WithdrawTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;


public class Menu {
    private static final Logger logger = LoggerFactory.getLogger(Menu.class);
    private static final Scanner scanner;
    private static final ApplicationContext context = ApplicationContextProvider.getApplicationContext();

    static {
        scanner = new Scanner(System.in);
    }

    public static void show() {
        int option = 0;
        boolean repeat;
        do {
            repeat = false;
            System.out.println("""
                    Choose an option:
                    1. Account creation with an initial balance
                    2. Perform a transaction
                    3. Display account balance
                    4. Exit
                    """);
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Not correct format");
                repeat = true;
                scanner.nextLine();
            }
            if (option == 1) {
                repeat = createAccount();
            } else if (option == 2) {
                performTransaction();
            } else if (option == 3) {
                displayBalance();
            } else if (option == 4) {
                return;
            } else if (option == 0) {
            } else {
                System.out.println("No such service!");
                repeat = true;
            }
        } while (option != 4 || repeat);
    }

    private static boolean createAccount() {
        BankServiceImpl bankService = context.getBean(BankServiceImpl.class);
        AccountServiceImpl accountService = context.getBean(AccountServiceImpl.class);
        String holderName = getHolderName();
        List<Bank> banks = bankService.findAll();
        banks.forEach(System.out::println);
        boolean repeat;
        do {
            repeat = false;
            try {
                System.out.println("In which bank do you want to create your account? Enter bank name: ");
                String selectedBankName = scanner.nextLine();
                Bank bank = bankService.findBankByName(selectedBankName);
                System.out.println("How much money do you want to deposit in your account?");
                double initialBalance = scanner.nextDouble();
                AccountDto accountDto = new AccountDto(holderName, initialBalance, bank);
                accountService.createAccount(accountDto);
            } catch (InputMismatchException e) {
                System.out.println("Not correct format");
                repeat = true;
                scanner.nextLine();
            } catch (NoBankFoundException | ExecutionException | InterruptedException e) {
                System.out.println(e.getMessage());
                repeat = true;
            }
        } while (repeat);
        return false;
    }

    private static void performTransaction() {
        AccountServiceImpl accountService = context.getBean(AccountServiceImpl.class);
        DepositTransaction depositTransaction = context.getBean(DepositTransaction.class);
        WithdrawTransaction withdrawTransaction = context.getBean(WithdrawTransaction.class);
        TransferFundTransaction transferFundTransaction = context.getBean(TransferFundTransaction.class);
        boolean repeat;
        do {
            repeat = false;
            try {
                String holderName = getHolderName();
                Account account = accountService.findAccountByName(holderName);
                System.out.println("""
                        What do you want to do?
                        1. Deposit
                        2. Withdraw
                        3. Transfer Funds
                        """);
                int transaction = scanner.nextInt();
                TransactionType transactionType = null;
                if (transaction == 1) {
                    transactionType = TransactionType.DEPOSIT;
                } else if (transaction == 2) {
                    transactionType = TransactionType.WITHDRAWAL;
                } else if (transaction == 3) {
                    transactionType = TransactionType.TRANSFER;
                } else {
                    System.out.println("No such service!");
                    repeat = true;
                }
                System.out.println("How much money do you want to transfer? ");
                double amount = scanner.nextDouble();
                TransactionDto transactionDto = new TransactionDto(null, LocalDateTime.now(), transactionType.name(), amount, account);
                if (Objects.equals(transactionType.name(), TransactionType.DEPOSIT.name())) {
                    accountService.setTransactionPerformer(depositTransaction);
                } else if (Objects.equals(transactionType.name(), TransactionType.WITHDRAWAL.name())) {
                    accountService.setTransactionPerformer(withdrawTransaction);
                } else {
                    System.out.println("To which account do you want to transfer? Enter account number: ");
                    long destinationAccountNumber = scanner.nextLong();
                    AccountDto destinationAccountDto = new AccountDto(destinationAccountNumber);
                    Account destinationAccount = accountService.findAccountById(destinationAccountDto);
                    transactionDto.setDestinationAccountNumber(destinationAccount);
                    accountService.setTransactionPerformer(transferFundTransaction);
                }
                accountService.perform(transactionDto);
            } catch (InputMismatchException i) {
                System.out.println("Not correct format");
                repeat = true;
                scanner.nextLine();
            } catch (NoAccountFoundException | ExecutionException | InterruptedException e) {
                System.out.println(e.getMessage());
                repeat = true;
            }
        } while (repeat);
    }

    private static void displayBalance() {
        AccountServiceImpl accountService = context.getBean(AccountServiceImpl.class);
        try {
            String holderName = getHolderName();
            Account account = accountService.findAccountByName(holderName);
            System.out.println("Balance: " + account.getBalance());
        } catch (NoAccountFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getHolderName() {
        System.out.println("Enter your name: ");
        scanner.nextLine();
        return scanner.nextLine();
    }

    public static void insertBanks() {
        BankServiceImpl bankService = context.getBean(BankServiceImpl.class);
        List<String> bankNames = Arrays.asList("Mellat", "Melli", "Tejarat");
        bankNames.forEach(bankService::create);
    }
}