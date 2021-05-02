package com.eureka.capstone.controller;

public enum Templates {
    LOGIN("login/login"),
    PRODUCTS("products/products"),
    SUBSYSTEMS("products/subsystems"),
    RELEASE_VERSIONS("products/release-versions"),
    USERS("users/users"),
    ISSUES("issues/issues"),
    EDIT_ISSUE("issues/edit-issue"),
    ADD_ISSUE("issues/add-issue"),
    REPORTS("reports/reports"),
    EDIT_PROFILE("users/edit-profile");

    private final String templateName;

    Templates(String templateName) {
        this.templateName = templateName;
    }

    public String getName() {
        return templateName;
    }
}
