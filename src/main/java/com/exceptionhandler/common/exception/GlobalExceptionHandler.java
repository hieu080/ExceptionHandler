package com.exceptionhandler.common.exception;

import com.exceptionhandler.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(Instant.now().toString())
                .status(ex.getHttpStatus())
                .errorCode(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .traceId(MDC.get("traceId")) // nếu có tích hợp logging
                .details(ex.getDetails())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(Instant.now().toString())
                .status(ErrorCode.SYSTEM_ERROR.getHttpStatus().value())
                .errorCode(ErrorCode.SYSTEM_ERROR.getCode())
                .message(ex.getMessage())
                .traceId(MDC.get("traceId"))
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(ErrorCode.SYSTEM_ERROR.getHttpStatus()).body(response);
    }
}
