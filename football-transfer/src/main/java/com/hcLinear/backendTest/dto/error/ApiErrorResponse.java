package com.hcLinear.backendTest.dto.error;

import java.util.List;

public record ApiErrorResponse(
        ApiErrorType type,
        String message,
        List<ApiErrorDetail> details
) {
}
