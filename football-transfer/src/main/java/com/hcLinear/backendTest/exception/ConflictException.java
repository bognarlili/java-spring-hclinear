package com.hcLinear.backendTest.exception;

import com.hcLinear.backendTest.dto.error.ApiErrorType;

public class ConflictException extends ApiException{

    public ConflictException(String message){
        super(ApiErrorType.CONFLICT, message, null);
    }
}
