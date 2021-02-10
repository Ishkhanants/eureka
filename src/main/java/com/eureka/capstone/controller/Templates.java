package com.eureka.capstone.controller;

public enum Templates {
    LANDING("landing"),
    LOGIN("login"),
    REGISTRATION("registration"),
    EDIT_PROFILE("edit-profile"),
    HOMEPAGE("calendar"),
    HOMEPAGE_ADMIN("calendarAdmin");

    private final String templateName;

    Templates(String templateName) {
        this.templateName = templateName;
    }

    public String getName() {
        return templateName;
    }
}
