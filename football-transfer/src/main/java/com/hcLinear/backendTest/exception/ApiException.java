package com.hcLinear.backendTest.exception;

import com.hcLinear.backendTest.dto.error.ApiErrorDetail;
import com.hcLinear.backendTest.dto.error.ApiErrorType;

import java.util.List;

public class ApiException extends RuntimeException{

    private final ApiErrorType type;
    private final List<ApiErrorDetail> details;


    public ApiException(ApiErrorType type, String message, List<ApiErrorDetail> details) {
        super(message);
        this.type = type;
        this.details = details == null ? List.of() : details;


    }

    public ApiErrorType getType() {
        return type;
    }

    public List<ApiErrorDetail> getDetails() {
        return details;
    }

}
