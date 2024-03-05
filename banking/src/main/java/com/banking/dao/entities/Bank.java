package com.banking.dao.entities;

import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class Bank extends BaseEntity<Long> {
    private String name;
}
