package org.demo.bankdemocore.domain;

import lombok.Getter;
import org.demo.bankdemocore.exception.TransactionError;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Account {
    private final String accountId;
    private final String accountNo;
    private final String accountName;
    private volatile BigDecimal amountCashHolding; //BigDecimal is already immutable

    public Account(int generationNumber) {
        accountId = UUID.randomUUID().toString();
        accountNo = "TEST" + LocalDate.now().toString().substring(0, 6);
        accountName = "TEST_" + generationNumber;
        amountCashHolding = BigDecimal.ZERO;
    }

    public synchronized void setMoneyAmount(BigDecimal cashAmount) throws TransactionError {

        if (amountCashHolding.add(cashAmount).compareTo(BigDecimal.ZERO) < 0) {
            throw new TransactionError(ErrorCode.ERROR_CODE_001);
        }

        amountCashHolding = amountCashHolding.add(cashAmount);
    }
}
