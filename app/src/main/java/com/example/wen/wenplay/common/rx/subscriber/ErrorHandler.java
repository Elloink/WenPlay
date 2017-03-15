package com.example.wen.wenplay.common.rx.subscriber;

import android.content.Context;
import android.widget.Toast;

import com.example.wen.wenplay.common.exception.ApiException;
import com.example.wen.wenplay.common.exception.BaseException;
import com.example.wen.wenplay.common.exception.ErrorMessageFactory;
import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * 处理Rx错误
 * Created by wen on 2017/3/10.
 */

public class ErrorHandler {

    private Context mContext;

    public ErrorHandler(Context context) {
        this.mContext = context;
    }

    public BaseException handleError(Throwable e) {
        BaseException baseException = new BaseException();

        if (e instanceof ApiException) {
            baseException.setCode(((ApiException) e).getCode());
        }
        else if (e instanceof JsonParseException) {
            baseException.setCode(BaseException.JSON_ERROR);
        }
        else if (e instanceof SocketException) {
            baseException.setCode(BaseException.SOCKET_ERROR);
        }
        else if (e instanceof SocketTimeoutException) {
            baseException.setCode(BaseException.SOCKET_TIME_OUT_ERROR);
        }
        else if (e instanceof HttpException) {
            baseException.setCode(BaseException.HTTP_ERROR);
        }
        else {
            baseException.setCode(BaseException.UNKNOW_ERROR);
        }

        baseException.setMsg(ErrorMessageFactory.create(mContext, baseException.getCode()));

        return baseException;

    }

    public void showErrorMessage(BaseException baseException){
        Toast.makeText(mContext,baseException.getMsg(),Toast.LENGTH_SHORT).show();
    }

}

