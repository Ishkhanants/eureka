package com.eureka.capstone.domain.issue;

public enum IssueSeverity {
    CRITICAL("Critical"),
    MAJOR("Major"),
    MINOR("Minor"),
    ULTRA_CRITICAL("Ultra Critical"),
    UNKNOWN("Unknown");

    private final String displayValue;

    IssueSeverity(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
