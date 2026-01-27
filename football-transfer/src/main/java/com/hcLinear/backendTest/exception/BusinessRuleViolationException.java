package com.hcLinear.backendTest.exception;

import com.hcLinear.backendTest.dto.error.ApiErrorType;

public class BusinessRuleViolationException extends ApiException{
    public BusinessRuleViolationException(String message) {
        super(ApiErrorType.BUSINESS_RULE_VIOLATION, message, null);
    }
}
