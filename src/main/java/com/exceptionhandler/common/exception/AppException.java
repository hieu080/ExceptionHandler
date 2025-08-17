package com.exceptionhandler.common.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Map<String, Object> details;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
        this.details = null;
    }

    public AppException(ErrorCode errorCode, String overrideMessage) {
        super(overrideMessage != null ? overrideMessage : errorCode.getDefaultMessage());
        this.errorCode = errorCode;
        this.details = null;
    }

    public AppException(ErrorCode errorCode, String overrideMessage, Map<String, Object> details) {
        super(overrideMessage != null ? overrideMessage : errorCode.getDefaultMessage());
        this.errorCode = errorCode;
        this.details = details;
    }

    public int getHttpStatus() {
        return errorCode.getHttpStatus().value();
    }
}

