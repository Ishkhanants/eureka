package com.eureka.capstone.domain.login;

import java.util.logging.*;

public class LoginFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        return String.valueOf(record.getLevel()) + '\n' + record.getMessage() + '\n';
    }

}
