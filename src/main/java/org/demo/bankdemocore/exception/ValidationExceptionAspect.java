package org.demo.bankdemocore.exception;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ValidationExceptionAspect {

    @AfterThrowing(pointcut = "execution(* org.demo.bankdemocore..*(..))", throwing = "ex")
    public void handleValidationException(ValidationException ex) {
        log.warn("Validation exception detected. message={}", ex.getMessage());
    }

    @AfterThrowing(pointcut = "execution(* org.demo.bankdemocore..*(..))", throwing = "ex")
    public void handleTransactionException(TransactionError ex) {
        log.warn("Transaction error detected. ErrorCode={}", ex.getErrorCode());
    }
}
