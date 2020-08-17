package com.pittosporum.constant.app;

import com.pittosporum.constant.ErrorCode;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public enum AppErrorCode implements ErrorCode {
    PARAMS_IS_EMPTY("B0001", "params is empty when call api"),
    DUPLICATE_RECORD("B0002", "This record already exists in the system."),
    EMPTY_OBJECT("B0003", "empty object"),
    FORMAT_SQL_ERROR("B0004", "an error occurred while format the sql"),
    USER_NOT_EXIST("B0005", "the user does not exist"),
    PASSWORD_ERROR("B0006", "password wrong"),
    PARAM_ERROR("B0007", "param has error"),
    NOT_LOGIN("B0008", "not login system"),
    RUN_JOB_FAILURE("B0009", "run job failure, because can not find job by id"),
    RUN_JOB_FAILURE_2("B0010", "run job failure, encountered an error"),
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
