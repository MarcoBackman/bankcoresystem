package org.demo.bankdemocore.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
public class Transaction {
    @NotNull
    private final Account account;
    @NotNull
    private final String transactionId;
    @NotNull
    private final BigDecimal tranAmount;
    @NotNull
    private final Side side;
    @Setter
    private LocalDateTime receivedWhen;
    @Setter
    private LocalDateTime processedWhen;
    @Setter
    private LocalDate settledDate;
    @Setter
    private String processStatus;
}
