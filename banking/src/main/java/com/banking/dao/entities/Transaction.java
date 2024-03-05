package com.banking.dao.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class Transaction extends BaseEntity<Long> {

    private LocalDateTime transactionDate;
    private String transactionType;
    private Double transactionAmount;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Account account;
}
