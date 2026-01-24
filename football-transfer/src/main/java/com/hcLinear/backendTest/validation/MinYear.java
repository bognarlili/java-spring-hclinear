package com.hcLinear.backendTest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.*;


@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinYearValidator.class)
public @interface MinYear {

    int value();
    String message() default "Year must be greater than or equal to {value}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
