package com.hcLinear.backendTest.validation;

import jakarta.validation.Payload;

public @interface MinAge {

    int value();

    String message() default "Player must be at least {value} years old";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
