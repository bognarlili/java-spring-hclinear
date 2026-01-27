package com.hcLinear.backendTest.exception;


import com.hcLinear.backendTest.dto.error.ApiErrorDetail;
import com.hcLinear.backendTest.dto.error.ApiErrorResponse;
import com.hcLinear.backendTest.dto.error.ApiErrorType;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<ApiErrorDetail> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ApiErrorDetail(err.getField(), err.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(
                new ApiErrorResponse(
                        ApiErrorType.VALIDATION_ERROR,
                        "Request validation failed",
                        details
                )
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<ApiErrorDetail> details = ex.getConstraintViolations()
                .stream()
                .map(v -> new ApiErrorDetail(v.getPropertyPath().toString(), v.getMessage()))
                .toList();

        return ResponseEntity.badRequest().body(
                new ApiErrorResponse(
                        ApiErrorType.VALIDATION_ERROR,
                        "Request validation failed",
                        details
                )
        );
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException ex) {
        HttpStatus status = switch (ex.getType()) {
            case VALIDATION_ERROR, BUSINESS_RULE_VIOLATION -> HttpStatus.BAD_REQUEST;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case CONFLICT -> HttpStatus.CONFLICT;
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        return ResponseEntity.status(status).body(
                new ApiErrorResponse(ex.getType(), ex.getMessage(), ex.getDetails())
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.warn("Data integrity violation", ex);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiErrorResponse(
                        ApiErrorType.CONFLICT,
                        "Conflict with existing data",
                        List.of()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception ex) {
        log.error("Unhandled exception", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorResponse(
                        ApiErrorType.INTERNAL_ERROR,
                        "Unexpected error occurred",
                        List.of()
                )
        );
    }
}
