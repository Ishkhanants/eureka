package com.eureka.capstone.domain.issue;

import java.util.Locale;

public enum IssueStatus {
    NEW("Նոր","New","Новая"),
    IN_WORK("Ընթացքում","In Work","В Работе"),
    FIXED("Լուծված","Fixed","Решена"),
    CLOSED("Փակված","Closed","Закрыта"),
    NEED_INFO("Ավել Տեղեկության Կարիք","Need Info","Нужны Доп. Сведения"),
    REJECTED("Մերժված","Rejected","Отклонена"),
    SUSPENDED("Կասեցված","Suspended", "Приостановлена"),
    TRASHED("Թափված","Trashed","Замусорована");

    private final String displayValue_AM;
    private final String displayValue_EN;
    private final String displayValue_RU;

    IssueStatus(String displayValue_AM, String displayValue_EN, String displayValue_RU) {
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
