package com.eureka.capstone.domain.issue;

public enum IssueStatus {
    NEW("New"),
    IN_WORK("In Work"),
    FIXED("Fixed"),
    CLOSED("Closed"),
    NEED_INFO("Need Info"),
    REJECTED("Rejected"),
    SUSPENDED("Suspended"),
    TRASHED("Trashed");

    private final String displayValue;

    IssueStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
