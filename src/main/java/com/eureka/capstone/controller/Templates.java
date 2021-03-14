package com.eureka.capstone.controller;

public enum Templates {
    LOGIN("login"),
    PRODUCTS("products"),
    SUBSYSTEMS("subsystems"),
    RELEASE_VERSIONS("release-versions"),
    USERS("users"),
    ISSUES("issues"),
    REPORTS("reports"),
    EDIT_PROFILE("edit-profile");

    private final String templateName;

    Templates(String templateName) {
        this.templateName = templateName;
    }

    public String getName() {
        return templateName;
    }
}
