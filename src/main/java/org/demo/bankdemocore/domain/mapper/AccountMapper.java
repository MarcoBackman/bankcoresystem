package org.demo.bankdemocore.domain.mapper;

import db.public_.tables.records.AccountRecord;
import org.demo.bankdemocore.domain.Account;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements RecordMapper<AccountRecord, Account> {
    @Override
    public Account map(AccountRecord record) {
        return Account.builder()
                .accountId(record.getAccountId())
                .accountNo(record.getAccountNo())
                .username(record.getUsername())
                .createdWhen(record.getCreatedWhen())
                .build();
    }
}
