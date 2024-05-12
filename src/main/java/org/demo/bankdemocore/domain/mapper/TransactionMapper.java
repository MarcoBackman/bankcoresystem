package org.demo.bankdemocore.domain.mapper;

import db.public_.tables.records.AccountRecord;
import db.public_.tables.records.TransactionRecord;
import lombok.extern.slf4j.Slf4j;
import org.demo.bankdemocore.repository.AccountDao;
import org.demo.bankdemocore.domain.Account;
import org.demo.bankdemocore.domain.Side;
import org.demo.bankdemocore.domain.Transaction;
import org.demo.bankdemocore.util.DateUtil;
import org.jetbrains.annotations.Nullable;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionMapper implements RecordMapper<TransactionRecord, Transaction> {

    DSLContext dsl;

    AccountMapper accountMapper;

    AccountDao accountDAO;

    TransactionMapper(DSLContext dsl,
                      AccountMapper accountMapper,
                      AccountDao accountDAO) {
        this.dsl = dsl;
        this.accountMapper = accountMapper;
        this.accountDAO = accountDAO;
    }
//
    @Nullable
    public Transaction map(TransactionRecord record, AccountRecord accountRecord) {
        Account account = Account.builder()
                .accountId(accountRecord.getAccountId())
                .accountNo(accountRecord.getAccountNo())
                .username(accountRecord.getUsername())
                .createdWhen(accountRecord.getCreatedWhen())
                .amountCashHolding(null) //TODO: set amount cash from other sources
                .build();

        if (account == null) {
            log.warn("Failed to get account data from transaction. accountId={}", accountRecord.getAccountId());
            return null;
        }

        return Transaction.builder()
                .account(account)
                .transactionId(Integer.toString(record.getTransactionId()))
                .tranAmount(record.getMoneyAmount())
                .side(Side.getSide(record.getSide()))
                .receivedWhen(record.getTranWhen())
                .processedWhen(record.getProcessedWhen())
                .settledDate(DateUtil.toLocalDate(record.getSettledWhen()))
                .processStatus(record.getStatus().getLiteral())
                .build();
    }

    /**
     * Non-preferred way of fetching data using nested transaction
     */
    @Nullable
    @Override
    public Transaction map(TransactionRecord record) {
        Account accountData = accountDAO.getAccountById(record.getAccountId());

        return Transaction.builder()
                .account(accountData)
                .transactionId(Integer.toString(record.getTransactionId()))
                .tranAmount(record.getMoneyAmount())
                .side(Side.valueOf(record.getSide()))
                .receivedWhen(record.getTranWhen())
                .processedWhen(record.getProcessedWhen())
                .settledDate(DateUtil.toLocalDate(record.getSettledWhen()))
                .processStatus(record.getStatus().getLiteral())
                .build();
    }
}
