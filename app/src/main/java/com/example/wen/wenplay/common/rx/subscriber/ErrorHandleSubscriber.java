package com.example.wen.wenplay.common.rx.subscriber;

import android.content.Context;

import com.example.wen.wenplay.common.exception.BaseException;

/**
 * 封装错误处理的Subscriber
 * Created by wen on 2017/3/9.
 */

public abstract class ErrorHandleSubscriber<T> extends DefaultSubscriber<T> {
    protected ErrorHandler mErrorHandler;
    private Context mContext;

    public ErrorHandleSubscriber(Context context) {

        this.mContext = context;

        mErrorHandler = new ErrorHandler(mContext);

    }

    @Override
    public void onError(Throwable e) {

        e.printStackTrace();

        BaseException baseException = mErrorHandler.handleError(e);

        mErrorHandler.showErrorMessage(baseException);

    }
}
