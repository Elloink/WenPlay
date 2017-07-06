package com.example.wen.wenplay.common.rx.subscriber;

import android.content.Context;
import android.content.Intent;

import com.example.wen.wenplay.common.exception.BaseException;
import com.example.wen.wenplay.ui.activity.LoginActivity;

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



        BaseException baseException = mErrorHandler.handleError(e);

        if (baseException == null){
            e.printStackTrace();
        }else {
            mErrorHandler.showErrorMessage(baseException);
            if (BaseException.ERROR_TOKEN == baseException.getCode()){
                toLogin();
            }

        }



    }

    private void toLogin() {
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
    }


}
