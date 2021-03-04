package com.eureka.capstone.domain.user;

public enum Group {
    SECURITY_ADMINS("Security Admins"),
    ADMINS_MANAGEMENT("Admins (Management)"),
    TESTERS("Testers"),
    ADMINS_DEVELOPMENT("Admins (Development)"),
    DEVELOPERS("Developers"),
    READ_ONLY_ACCESS("Read Only Access");

    private final String displayValue;

    private Group(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
