package com.eureka.capstone.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = com.eureka.capstone.validation.PasswordMatchingValidator.class)
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface MatchingPassword {

    String message() default "{valid.password.matching.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}