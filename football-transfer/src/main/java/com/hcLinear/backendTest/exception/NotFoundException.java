package com.hcLinear.backendTest.exception;

import com.hcLinear.backendTest.dto.error.ApiErrorType;

public class NotFoundException extends ApiException{
    public NotFoundException(String message){
        super(ApiErrorType.NOT_FOUND, message, null);
    }
}
