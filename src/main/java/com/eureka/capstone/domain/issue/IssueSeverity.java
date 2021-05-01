package com.eureka.capstone.domain.issue;

import java.util.Locale;

public enum IssueSeverity {
    ULTRA_CRITICAL("Անհետաձգելի","Ultra Critical","Ультракритический"),
    CRITICAL("Հրատապ","Critical","Критический"),
    MAJOR("Կարևոր","Major","Важный"),
    MINOR("Ոչ Էական","Minor","Незначительный"),
    UNKNOWN("Անհայտ","Unknown","Неизвестный");

    private final String displayValue_AM;
    private final String displayValue_EN;
    private final String displayValue_RU;

    IssueSeverity(String displayValue_AM, String displayValue_EN, String displayValue_RU) {
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
