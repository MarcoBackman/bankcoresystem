package org.demo.bankdemo.exception;

import org.demo.bankdemo.domain.ErrorCode;

public class TransactionError extends Exception {

    private final ErrorCode errorCode;

    public TransactionError(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode.getCode();
    }

    public String getMessage() {
        return errorCode.getMessage();
    }
}
