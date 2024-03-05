package com.banking.mapper;

import com.banking.dao.entities.Account;
import com.banking.dao.entities.dto.AccountDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AccountMapper {
    Account accountDtoToAccount(AccountDto dto);

    AccountDto accountToAccountDto(Account entity);
}