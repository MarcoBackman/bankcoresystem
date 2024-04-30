package org.demo.bankdemocore.exception;

import org.demo.bankdemocore.domain.ErrorCode;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptionHandlerTest {

    private void throwingValidationException() throws ValidationException {
        throw new ValidationException();
    }

    private void throwingTransactionException() throws TransactionError {
        throw new TransactionError(ErrorCode.ERROR_CODE_001);
    }

    private void mockCompositeException(boolean isValidationException) throws Exception {
        if (isValidationException) {
            throwingValidationException();
        } else {
            throwingTransactionException();
        }
    }

    @Test
    public void getTestForException() {
        Throwable validationException = assertThrows(ValidationException.class, () -> {
            mockCompositeException(true);
        });

        assertNotNull(validationException);

        TransactionError transactionException = assertThrows(TransactionError.class, () -> {
            mockCompositeException(false);
        });

        assertNotNull(transactionException);
        assertThat(transactionException.getErrorCode()).isEqualTo(ErrorCode.ERROR_CODE_001.getCode());
        assertThat(transactionException.getMessage()).isEqualTo(ErrorCode.ERROR_CODE_001.getMessage());
    }
}
