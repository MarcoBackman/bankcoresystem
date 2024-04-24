package org.demo.bankdemo.domain;

import lombok.Getter;

@Getter
public enum ErrorCode {

    ERROR_CODE_001("001", "Account has insufficient amount of money"),
    ERROR_CODE_002("002", "Zero amount transaction given"),
    ERROR_CODE_003("003", "Unsupported transaction operation type");

    public final String code;
    public final String message;

    private ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("Error Code=%s, Message=%s", code, message);
    }
}
