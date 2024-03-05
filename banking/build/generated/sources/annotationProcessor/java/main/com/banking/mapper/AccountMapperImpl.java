package com.banking.mapper;

import com.banking.dao.entities.Account;
import com.banking.dao.entities.Account.AccountBuilder;
import com.banking.dao.entities.dto.AccountDto;
import com.banking.dao.entities.dto.AccountDto.AccountDtoBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-05T11:42:35+0330",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.10 (Amazon.com Inc.)"
)
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account accountDtoToAccount(AccountDto dto) {
        if ( dto == null ) {
            return null;
        }

        AccountBuilder account = Account.builder();

        account.accountHolderName( dto.getAccountHolderName() );
        account.balance( dto.getBalance() );
        account.bank( dto.getBank() );

        return account.build();
    }

    @Override
    public AccountDto accountToAccountDto(Account entity) {
        if ( entity == null ) {
            return null;
        }

        AccountDtoBuilder accountDto = AccountDto.builder();

        accountDto.id( entity.getId() );
        accountDto.accountHolderName( entity.getAccountHolderName() );
        accountDto.balance( entity.getBalance() );
        accountDto.bank( entity.getBank() );

        return accountDto.build();
    }
}
