package com.eureka.capstone.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Size(min = 6, max = 20)
@Pattern(regexp = "((?=.*\\d)(?=.*\\p{Lu})(?=.*\\p{Ll}))[\\p{L}\\d\\p{Punct}\\p{Blank}]*")
@ReportAsSingleViolation
@Constraint(validatedBy = {})
@Target({FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
public @interface ValidPassword {

    String message() default "{valid.password.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
