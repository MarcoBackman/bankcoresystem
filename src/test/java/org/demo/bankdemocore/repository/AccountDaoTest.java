package org.demo.bankdemocore.repository;

import db.public_.tables.records.AccountRecord;
import org.demo.bankdemocore.domain.Account;
import org.demo.bankdemocore.domain.mapper.AccountMapper;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static db.public_.Tables.ACCOUNT;
import static org.assertj.core.api.Assertions.assertThat;

@Import({AccountDao.class, AccountMapper.class})
@BlockingDatabaseTest
@ExtendWith(MockitoExtension.class)
public class AccountDaoTest {

    @Autowired
    DSLContext dsl;

    @Autowired
    AccountDao accountDao;

    LocalDate testDate = LocalDate.of(2024, 5,12);
    LocalTime testTime = LocalTime.of(15,23);
    LocalDateTime testDatetime = LocalDateTime.of(testDate, testTime);

    private void addMockAccount() {

        AccountRecord record = new AccountRecord();
        record.setAccountId("testAccountId");
        record.setAccountNo("testNo");
        record.setUsername("username");
        record.setCreatedWhen(testDatetime);

        dsl.insertInto(ACCOUNT).set(record).execute();
    }

    @Test
    public void test_getAccountById() {
        addMockAccount();

        Account account = accountDao.getAccountById("testAccountId");
        assertThat(account).isNotNull();
        assertThat(account.getAccountNo()).isEqualTo("testNo");
    }
}
