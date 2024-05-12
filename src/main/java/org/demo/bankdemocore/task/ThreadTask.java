package org.demo.bankdemocore.task;

import lombok.extern.slf4j.Slf4j;
import org.demo.bankdemocore.domain.Account;
import org.demo.bankdemocore.domain.Side;
import org.demo.bankdemocore.domain.Transaction;
import org.demo.bankdemocore.exception.TransactionError;

import java.time.LocalDateTime;
import java.util.List;

import static org.demo.bankdemocore.domain.ErrorCode.ERROR_CODE_003;

/**
 * This is inappropriate thread since it can be created multiple times
 */
@Slf4j
public class ThreadTask<T> extends Thread {

    private final List<T> listObject;

    public ThreadTask(List<T> listObject) {
        this.listObject = listObject;
        this.setDaemon(false);
    }

    @Override
    public void run() {

        listObject.forEach(object -> {
            if (object instanceof Transaction transaction) {
                log.info("Processing Thread transaction. accountNo={}", transaction.getAccount().getAccountNo());
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
                transaction.setProcessedWhen(LocalDateTime.now());
                transaction.setProcessStatus("SUCCESS");
                log.info("Process Completed for transactionId={}, side={}, tranAmount={}, amountHolding={}",
                        transaction.getTransactionId(),
                        transaction.getSide().name(),
                        transaction.getTranAmount(),
                        account.getAmountCashHolding());
            } else {
                log.warn("Object {} not supported", object.getClass());
            }
        });
    }

}
