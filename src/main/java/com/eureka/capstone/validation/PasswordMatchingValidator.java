package com.eureka.capstone.validation;


import com.eureka.capstone.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchingValidator implements ConstraintValidator<MatchingPassword, UserDto> {
    public void initialize(MatchingPassword constraint) {
    }

    @Override
    public boolean isValid(UserDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }
        if (dto.getPassword() == null) {
            return true;
        }
        return dto.getPassword().equals(dto.getConfirmPassword());
    }
}
