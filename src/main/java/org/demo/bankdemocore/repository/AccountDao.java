package org.demo.bankdemocore.repository;

import org.demo.bankdemocore.domain.Account;
import org.demo.bankdemocore.domain.mapper.AccountMapper;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountDao implements IDaoPattern {

    DSLContext dsl;
    AccountMapper accountMapper;

    AccountDao(DSLContext dsl,
               AccountMapper accountMapper) {
        this.dsl = dsl;
        this.accountMapper = accountMapper;
    }

    public Account getAccountById(String accountId) {
        return dsl.selectFrom(db.public_.Tables.ACCOUNT)
                .where(db.public_.Tables.ACCOUNT.ACCOUNT_ID.eq(accountId))
                .fetchOne(accountRecord -> accountMapper.map(accountRecord));
    }

    @Override
    public Optional get(long id) {
        //Todo: implement
        return Optional.empty();
    }

    @Override
    public List getAll() {
        //Todo: implement
        return null;
    }

    @Override
    public void save(Object object) {
        //Todo: implement
    }

    @Override
    public void update(Object object, String[] params) {
        //Todo: implement
    }

    @Override
    public void delete(Object object) {
        //Todo: implement
    }
}
