package org.demo.bankdemocore.repository;

import db.public_.enums.TransactionStatus;
import db.public_.tables.records.AccountRecord;
import db.public_.tables.records.TransactionRecord;
import org.demo.bankdemocore.domain.Transaction;
import org.demo.bankdemocore.domain.Side;
import org.demo.bankdemocore.domain.mapper.AccountMapper;
import org.demo.bankdemocore.domain.mapper.TransactionMapper;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static db.public_.Tables.ACCOUNT;
import static db.public_.Tables.TRANSACTION;
import static org.assertj.core.api.Assertions.assertThat;

@Import({TransactionDao.class, AccountDao.class, AccountMapper.class, TransactionMapper.class})
@BlockingDatabaseTest
@ExtendWith(MockitoExtension.class)
public class TransactionDaoTest {

    @Autowired
    DSLContext dsl;

    @Autowired
    @InjectMocks
    TransactionDao transactionDao;

    @Autowired
    AccountDao accountDao;

    LocalDate testDate = LocalDate.of(2024, 5,12);
    LocalTime testTime = LocalTime.of(15,23);
    LocalDateTime tranDatetime = LocalDateTime.of(testDate, testTime);
    String testAccountId = "testAccountId";

    @BeforeEach
    public void init() {
        addAccount();
    }

    private void addAccount() {
        AccountRecord record = new AccountRecord();
        record.setAccountId(testAccountId);
        record.setAccountNo("testNo");
        record.setUsername("username");
        record.setCreatedWhen(tranDatetime);

        dsl.insertInto(ACCOUNT).set(record).execute();
    }

    private void addMockTransaction(int tranId, BigDecimal moneyAmt, Side side) {
        TransactionRecord transaction = new TransactionRecord();
        transaction.setTransactionId(tranId);
        transaction.setMoneyAmount(moneyAmt);
        transaction.setSide(side.getCode());
        transaction.setProcessedWhen(null);
        transaction.setTranWhen(tranDatetime);
        transaction.setSettledWhen(null);
        transaction.setAccountId(testAccountId);
        transaction.setStatus(TransactionStatus.NEW);

        dsl.insertInto(TRANSACTION).set(transaction).execute();
    }

    @Test
    public void getTransactionByIdTest() {
        addMockTransaction(123, new BigDecimal("152.35"), Side.DEPOSIT);
        addMockTransaction(234, new BigDecimal("112.35"), Side.WITHDRAW);
        addMockTransaction(345, new BigDecimal("25152.35"), Side.DEPOSIT);

        Transaction actualTransaction = transactionDao.getTransactionById(123);
        assertThat(actualTransaction).isNotNull();
        assertThat(actualTransaction.getSide()).isEqualTo(Side.DEPOSIT);
        assertThat(actualTransaction.getTranAmount()).isEqualByComparingTo(new BigDecimal("152.35"));
        assertThat(actualTransaction.getAccount()).isNotNull();
        assertThat(actualTransaction.getAccount().getAccountId()).isEqualTo(testAccountId);
    }
}
