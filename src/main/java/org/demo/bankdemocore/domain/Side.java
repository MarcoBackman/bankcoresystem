package org.demo.bankdemocore.domain;

/**
 * Side notion cannot exceed two characters
 */
public enum Side {

    WITHDRAW("WD"),
    DEPOSIT("DP");

    private final String sideNotation;

    Side(String sideNotation) {
        this.sideNotation = sideNotation;
    }

    public String getCode() {
        return this.sideNotation;
    }

    public static Side getSide(String code) {
        Side side;
        switch (code) {
            case "WD" -> side = WITHDRAW;
            case "DP" -> side = DEPOSIT;
            default -> throw new RuntimeException("No enum constant found");
        }
        return side;
    }
}
