package com.pittosporum.constant;

import pittosporum.constant.ErrorCode;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public enum AppErrorCode implements ErrorCode {
    DUPLICATE_RECORD("B0001", "This record already exists in the system."),
    ;

    private final String statusCode;
    private final String message;

    AppErrorCode(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }


    public String getStatusCode() {
        return this.statusCode;
    }

    public String getMessage() {
        return this.message;
    }
}
