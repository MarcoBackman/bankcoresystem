package org.demo.bankdemocore.repository;

import db.public_.tables.records.AccountRecord;
import db.public_.tables.records.TransactionRecord;
import jakarta.annotation.Nullable;
import org.demo.bankdemocore.domain.Transaction;
import org.demo.bankdemocore.domain.mapper.TransactionMapper;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static db.public_.Tables.ACCOUNT;
import static db.public_.Tables.TRANSACTION;

@Service
public class TransactionDao implements IDaoPattern {

    DSLContext dsl;
    TransactionMapper transactionMapper;

    TransactionDao(DSLContext dsl,
                   TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
        this.dsl = dsl;
    }

    @Nullable
    public Transaction getTransactionById(Integer tranId) {
        return dsl.select()
                .from(TRANSACTION)
                .innerJoin(
                        ACCOUNT).on(TRANSACTION.ACCOUNT_ID.eq(ACCOUNT.ACCOUNT_ID))
                .where(TRANSACTION.TRANSACTION_ID.eq(tranId))
                .fetchOne(record -> {
                    AccountRecord accountRecord = record.into(ACCOUNT);
                    TransactionRecord transactionRecord = record.into(TRANSACTION);
                    return transactionMapper.map(transactionRecord, accountRecord);
                });
    }

    @Override
    public Optional<Transaction> get(long id) {
        CompletionStage<Result<Record>> completableFuture = dsl.select()
                .from(TRANSACTION)
                .innerJoin(ACCOUNT).on(TRANSACTION.ACCOUNT_ID.eq(ACCOUNT.ACCOUNT_ID))
                .where(TRANSACTION.TRANSACTION_ID.eq((int) id))
                .fetchAsync();
        return Optional.of(
                    completableFuture.handle((records, throwable) -> {
                        TransactionRecord transactionRecord = records.into(TRANSACTION).get(0);
                        AccountRecord accountRecord = records.into(ACCOUNT).get(0);
                        return transactionMapper.map(transactionRecord, accountRecord);
                    }).toCompletableFuture().join()
                );
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
