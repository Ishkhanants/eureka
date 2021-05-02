package com.eureka.capstone.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Size(min = 5, max = 20)
@Pattern(regexp = "^[a-zA-Z0-9]*$")
@ReportAsSingleViolation
@Constraint(validatedBy = {})
@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidUsername {

    String message() default "{valid.username.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
