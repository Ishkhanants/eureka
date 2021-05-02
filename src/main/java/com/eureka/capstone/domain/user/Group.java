package com.eureka.capstone.domain.user;

import java.util.Locale;

public enum Group {
    SECURITY_ADMINS("Անվտանգության Ադմիններ","Security Admins","Админы Безопасности"),
    ADMINS_MANAGEMENT("Ադմիններ (Կառավարում)", "Admins (Management)", "Админы (Управление)"),
    TESTERS("Թեստավորողներ","Testers","Тестировщики"),
    ADMINS_DEVELOPMENT("Ադմիններ (Ծրագրավորում)","Admins (Development)","Админы (Разработка)"),
    DEVELOPERS("Ծրագրավորողներ","Developers","Разработчики"),
    READ_ONLY_ACCESS("Միայն Կարդալու Հասանելիություն","Read Only Access","Доступ Только Чтения");

    private final String displayValue_AM;
    private final String displayValue_EN;
    private final String displayValue_RU;

    Group(String displayValue_AM, String displayValue_EN, String displayValue_RU) {
        this.displayValue_AM = displayValue_AM;
        this.displayValue_EN = displayValue_EN;
        this.displayValue_RU = displayValue_RU;
    }

    public String getValueByLocale(Locale locale) {
        switch (locale.getLanguage().toUpperCase()) {
            case "RU":
                return displayValue_RU;
            case "EN":
                return displayValue_EN;
            default:
                return displayValue_AM;
        }
    }
}
