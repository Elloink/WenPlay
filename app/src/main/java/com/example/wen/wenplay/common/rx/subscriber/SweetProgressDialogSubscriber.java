package com.example.wen.wenplay.common.rx.subscriber;

import android.content.Context;

import com.example.wen.wenplay.common.exception.BaseException;
import com.example.wen.wenplay.common.util.ProgressDialogHandler;
import com.example.wen.wenplay.ui.BaseView;

import io.reactivex.disposables.Disposable;

/**
 * Created by wen on 2017/3/11.
 */

public abstract class SweetProgressDialogSubscriber<T> extends ErrorHandleSubscriber<T> implements ProgressDialogHandler.OnProgressCancelListener{


   private Disposable mDisposable;

    private BaseView mBaseView;

    public SweetProgressDialogSubscriber(Context context, BaseView baseView) {
        super(context);
        this.mBaseView = baseView;
    }

    /**
     * 是否需要显示ProgressDialog
     * 可以通过重写return false 表示不显示
     */
    protected boolean isShowProgressDialog(){
        return true;
    }

    @Override
    public void onSubscribe(Disposable d) {

        mDisposable = d;

      mBaseView.showLoading();

    }

    @Override
    public void onError(Throwable e) {

        BaseException baseException = mErrorHandler.handleError(e);

        mBaseView.showError(baseException.getMsg());

    }

    @Override
    public void onComplete() {

        mBaseView.dismissLoading();
    }

    @Override
    public void onCancelProgress() {
        //取消的时候取消订阅
        mDisposable.dispose();
    }
}
