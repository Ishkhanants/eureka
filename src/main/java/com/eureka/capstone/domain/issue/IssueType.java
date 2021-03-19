package com.eureka.capstone.domain.issue;

public enum IssueType {
    PROBLEM("Problem"),
    ENHANCEMENT("Enhancement");

    private final String displayValue;

    IssueType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
