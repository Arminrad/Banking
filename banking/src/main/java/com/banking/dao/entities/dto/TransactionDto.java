package com.banking.dao.entities.dto;

import com.banking.dao.entities.Account;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class TransactionDto {

    private Account destinationAccountNumber;
    private LocalDateTime transactionDate;
    private String transactionType;
    private Double transactionAmount;
    private Account account;
}
