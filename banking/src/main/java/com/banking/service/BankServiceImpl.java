package com.banking.service;

import com.banking.dao.BankRepository;
import com.banking.dao.entities.Bank;
import com.banking.exception.NoBankFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Transactional
    public Bank create(String bankName) {
        Bank bank = new Bank(bankName);
        return bankRepository.save(bank);
    }

    public List<Bank> findAll() {
        List<Bank> banks = bankRepository.findAll();
        if (banks.isEmpty()) {
            throw new RuntimeException("No Banks are available in the list!");
        }
        return banks;
    }

    public Bank findById(Long id) {
        return bankRepository.findById(id).orElseThrow(() -> new RuntimeException("Bank with that id is not available"));
    }

    public Bank findBankByName(String name) {
        Bank bank = bankRepository.findBankByName(name);
        if (bank == null) {
            throw new NoBankFoundException();
        }
        return bank;
    }
}
