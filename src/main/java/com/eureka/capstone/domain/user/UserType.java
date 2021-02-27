package com.eureka.capstone.domain.user;

public enum UserType {
    DEVELOPER("Developer"),
    TESTER("Tester"),
    MANAGER("Manager"),
    USER("User");

    private final String displayValue;

    private UserType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
