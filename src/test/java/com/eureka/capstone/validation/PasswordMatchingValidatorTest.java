package com.eureka.capstone.validation;

import com.eureka.capstone.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({MockitoExtension.class})
class PasswordMatchingValidatorTest {
    @Spy
    PasswordMatchingValidator validator;
    @Mock
    ConstraintValidatorContext constraintValidatorContext;
    @Spy
    private UserDto dto;

    @Test
    void Should_ReturnTrue_When_PasswordsMatch() {
        dto.setPassword("12345Abc");
        dto.setConfirmPassword("12345Abc");
        boolean isValid = validator.isValid(dto, constraintValidatorContext);
        assertTrue(isValid);
    }

    @Test
    void Should_ReturnFalse_When_PasswordsDoNotMatch() {
        dto.setPassword("12345Abc");
        dto.setConfirmPassword("54882d5Abc");
        boolean isValid = validator.isValid(dto, constraintValidatorContext);
        assertFalse(isValid);
    }

}