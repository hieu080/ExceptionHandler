package com.exceptionhandler.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // AUTH
    AUTH_INVALID_TOKEN("AUTH_001", "Token không hợp lệ", HttpStatus.UNAUTHORIZED),
    AUTH_UNAUTHORIZED("AUTH_002", "Không có quyền truy cập", HttpStatus.FORBIDDEN),
    AUTH_INVALID_CREDENTIALS("AUTH_003", "Sai username hoặc password", HttpStatus.UNAUTHORIZED),

    // USER
    USER_NOT_FOUND("USER_001", "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    USER_CONFLICT("USER_002", "Trạng thái người dùng không hợp lệ", HttpStatus.CONFLICT),

    // VALIDATION
    VALIDATION_FAILED("VALID_001", "Dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST),

    // SYSTEM
    SYSTEM_ERROR("SYS_001", "Lỗi hệ thống nội bộ", HttpStatus.INTERNAL_SERVER_ERROR),
    EXTERNAL_SERVICE_ERROR("SYS_002", "Lỗi dịch vụ bên ngoài", HttpStatus.BAD_GATEWAY),

    // HTTP
    HTTP_NOT_FOUND("HTTP_404", "API không tồn tại", HttpStatus.NOT_FOUND),
    HTTP_METHOD_NOT_ALLOWED("HTTP_405", "Phương thức không được hỗ trợ", HttpStatus.METHOD_NOT_ALLOWED),
    HTTP_BAD_REQUEST("HTTP_400", "Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;
}
