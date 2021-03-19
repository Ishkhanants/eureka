package com.eureka.capstone.domain.issue;

public enum IssueStatus {
    CLOSED("Closed"),
    CLOSED_AR("Closed AR"),
    FIXED("Fixed"),
    IN_WORK("In Work"),
    NEED_INFO("Need Info"),
    NEW("New"),
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
