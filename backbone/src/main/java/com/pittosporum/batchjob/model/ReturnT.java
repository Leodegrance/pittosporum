package com.pittosporum.batchjob.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public class ReturnT<T> {
    public static final int FAIL_CODE = 500;
    public static final ReturnT<String> SUCCESS = new ReturnT(null);
    public static final ReturnT<String> FAIL = new ReturnT(500, null);
    private int code;
    private String msg;
    private T content;

    public ReturnT() {
    }

    public ReturnT(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ReturnT(T content) {
        this.code = 200;
        this.content = content;
        this.msg = "start job success";
    }
}
