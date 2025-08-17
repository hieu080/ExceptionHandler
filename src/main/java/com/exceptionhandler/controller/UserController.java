package com.exceptionhandler.controller;

import com.exceptionhandler.common.exception.AppException;
import com.exceptionhandler.common.exception.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/test/app-exception")
    public String testAppException() {
        // Cố tình ném lỗi AppException
        throw new AppException(ErrorCode.USER_NOT_FOUND, "Không tìm thấy user id=999");
    }

    @GetMapping("/api/test/general-exception")
    public String testGeneralException() {
        // Cố tình ném lỗi NullPointerException
        String x = null;
        return x.toString(); // lỗi NPE
    }
}
