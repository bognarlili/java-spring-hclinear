package com.hcLinear.backendTest.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class MinYearValidator implements ConstraintValidator<MinYear, Year> {

    private int min;

    @Override
    public void initialize(MinYear constraintAnnotation) {
        this.min = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Year value, ConstraintValidatorContext context) {
        return value.getValue() >= min;
    }
}
