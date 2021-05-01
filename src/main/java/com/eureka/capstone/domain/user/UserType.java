package com.eureka.capstone.domain.user;

import java.util.Locale;

public enum UserType {
    DEVELOPER("Ծրագրավորող", "Developer", "Разработчик"),
    TESTER("Թեստավորող","Tester", "Тестировщик"),
    MANAGER("Կառավարիչ","Manager", "Менеджер"),
    USER("Օգտագործող","User", "Пользователь");

    private final String displayValue_AM;
    private final String displayValue_EN;
    private final String displayValue_RU;

    UserType(String displayValue_AM, String displayValue_EN, String displayValue_RU) {
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
