package com.banking.dao.entities.dto;

import com.banking.dao.entities.Bank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AccountDto {

    private Long id;
    private String accountHolderName;
    private double balance;
    private Bank bank;

    public AccountDto(String accountHolderName, double balance, Bank bank) {
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.bank = bank;
    }

    public AccountDto(Long id) {
        this.id = id;
    }
}
