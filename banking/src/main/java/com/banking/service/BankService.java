package com.banking.service;

import com.banking.dao.entities.Bank;

import java.util.List;

public interface BankService {

    Bank create(String bankName);
    List<Bank> findAll();
    Bank findById(Long id);
    Bank findBankByName(String name);
}
