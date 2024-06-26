package org.demo.bankdemocore.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demo.bankdemocore.domain.Account;
import org.demo.bankdemocore.domain.Side;
import org.demo.bankdemocore.domain.Transaction;
import org.demo.bankdemocore.exception.TransactionError;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

import static org.demo.bankdemocore.domain.ErrorCode.ERROR_CODE_003;

/**
 * Let's assume this is a transaction processor
 */
@Slf4j
@RequiredArgsConstructor
public class CallableTask implements Callable<Transaction> {

    private final Transaction transaction;


    @Override
    public Transaction call() {
        log.info("Processing Callable transaction. accountNo={}, Side={}, Amount={}, TranId={}",
                transaction.getAccount().getAccountNo(),
                transaction.getSide().name(),
                transaction.getTranAmount(),
                transaction.getTransactionId());

        Account account = transaction.getAccount();

        try {
            if (transaction.getSide().equals(Side.WITHDRAW)) {
                account.setMoneyAmount(transaction.getTranAmount().negate());
            } else if (transaction.getSide().equals(Side.DEPOSIT)) {
                account.setMoneyAmount(transaction.getTranAmount());
            } else {
                throw new TransactionError(ERROR_CODE_003);
            }
            log.info("Processing a time consuming operation...");
            Thread.sleep(2000); //Assume this is an operation...

        } catch (TransactionError transactionError) {
            log.warn("Error on transactionId={}, side={}, tranAmount={}, amountHolding={}, Code={}, Cause={}",
                    transaction.getSide().name(),
                    transaction.getTransactionId(),
                    transaction.getTranAmount(),
                    account.getAmountCashHolding(),
                    transactionError.getErrorCode(),
                    transactionError.getMessage());
            throw new RuntimeException(transactionError.getErrorCode());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        transaction.setProcessedWhen(LocalDateTime.now());
        log.info("Process Completed for transactionId={}, side={}, tranAmount={}, amountHolding={}",
                transaction.getTransactionId(),
                transaction.getSide().name(),
                transaction.getTranAmount(),
                account.getAmountCashHolding());
        return transaction;
    }
}
