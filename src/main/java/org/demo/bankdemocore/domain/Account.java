package org.demo.bankdemocore.domain;

import lombok.Builder;
import lombok.Getter;
import org.demo.bankdemocore.exception.TransactionError;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class Account {
    private String accountId;
    private String accountNo;
    private String username;
    private LocalDateTime createdWhen;
    private volatile BigDecimal amountCashHolding; //BigDecimal is already immutable

    public Account createNewAccount(int generationNumber) {
        accountId = UUID.randomUUID().toString();
        accountNo = accountId.substring(0, 2) + LocalDate.now().toString().substring(0, 6);
        amountCashHolding = BigDecimal.ZERO; //TODO: retrieve account's amount cash from different source
        return this;
    }

    public synchronized void setMoneyAmount(BigDecimal cashAmount) throws TransactionError {

        if (amountCashHolding.add(cashAmount).compareTo(BigDecimal.ZERO) < 0) {
            throw new TransactionError(ErrorCode.ERROR_CODE_001);
        }

        amountCashHolding = amountCashHolding.add(cashAmount);
    }
}
