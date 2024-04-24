package org.demo.bankdemo.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demo.bankdemo.domain.Account;
import org.demo.bankdemo.domain.Side;
import org.demo.bankdemo.domain.Transaction;
import org.demo.bankdemo.exception.TransactionError;

import java.time.OffsetDateTime;

import static org.demo.bankdemo.domain.ErrorCode.ERROR_CODE_003;

@Slf4j
@RequiredArgsConstructor
public class RunnableTask implements Runnable {

    private final Transaction transaction;

    @Override
    public void run() {
        log.info("Processing Runnable transaction. accountNo={}, Side={}, Amount={}, TranId={}",
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
            Thread.sleep(2000);
        } catch (TransactionError transactionError) {
            log.warn("Error on transactionId={}, side={}, tranAmount={}, amountHolding={}, Code={}, Cause={}",
                    transaction.getSide().name(),
                    transaction.getTransactionId(),
                    transaction.getTranAmount(),
                    account.getAmountCashHolding(),
                    transactionError.getErrorCode(),
                    transactionError.getMessage());

            transaction.setProcessStatus("FAILED");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        transaction.setProcessedWhen(OffsetDateTime.now());
        transaction.setProcessStatus("SUCCESS");
        log.info("Process Completed for transactionId={}, side={}, tranAmount={}, amountHolding={}",
                transaction.getTransactionId(),
                transaction.getSide().name(),
                transaction.getTranAmount(),
                account.getAmountCashHolding());
    }
}
