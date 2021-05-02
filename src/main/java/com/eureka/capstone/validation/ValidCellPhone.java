package com.eureka.capstone.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Pattern(regexp = "^\\+374[\\d]{7,10}$", message = "{valid.cellphone.message}")
@Constraint(validatedBy = {})
@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidCellPhone {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
