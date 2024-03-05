package com.banking.dao.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class Account extends BaseEntity<Long> {

    private String accountHolderName;
    private double balance;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Bank bank;
}
