package com.eureka.capstone.exception.notunique;

import java.util.Map;

public class FieldsAlreadyExistException extends RuntimeException{
    Map<String, Boolean> fieldsErrors;

    public FieldsAlreadyExistException(Map<String, Boolean> fieldsErrors) {
        super();
        this.fieldsErrors = fieldsErrors;
    }

    public FieldsAlreadyExistException(String message) {
        super(message);
    }

    public FieldsAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public Map<String, Boolean> getFieldsErrors() {
        return fieldsErrors;
    }
}
