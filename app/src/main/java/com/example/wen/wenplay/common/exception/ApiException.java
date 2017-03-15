package com.example.wen.wenplay.common.exception;

/**
 * API异常
 * Created by wen on 2017/3/7.
 */

public class ApiException extends BaseException {

    public ApiException(int code, String msg) {
        super(code, msg);
    }


}
