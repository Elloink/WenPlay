package com.example.wen.wenplay.common.rx.subscriber;


import android.content.Context;

import com.example.wen.wenplay.ui.BaseView;

import io.reactivex.disposables.Disposable;

/**
 * 简单处理progressDialog Subscriber
 * Created by wen on 2017/3/10.
 */

public abstract class ProgressDialogSubscriber<T> extends ErrorHandleSubscriber<T> {

    private BaseView mBaseView;

    /**
     * 是否需要显示ProgressDialog
     * 可以通过重写return false 表示不显示
     */
    protected boolean isShowProgressDialog(){
        return true;
    }

    public ProgressDialogSubscriber(Context context ,BaseView baseView) {

        super(context);

        this.mBaseView = baseView;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (isShowProgressDialog()) {
            showProgressDialog();
        }
    }



    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (isShowProgressDialog()){
            dismissProgressDialog();
        }
    }

    @Override
    public void onComplete() {
        if (isShowProgressDialog()){
            dismissProgressDialog();
        }

    }


    private void showProgressDialog() {
        mBaseView.showLoading();
    }

    private void dismissProgressDialog() {
        mBaseView.dismissLoading();
    }



}
