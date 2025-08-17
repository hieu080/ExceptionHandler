package com.exceptionhandler.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    @Builder.Default
    private String timestamp = Instant.now().toString();
    private int status;
    private String errorCode;
    private String message;
    private String traceId;
    private Map<String, Object> details;
    private String path;
}

