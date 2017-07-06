package com.example.wen.wenplay.presenter;

import com.example.wen.wenplay.bean.LoginBean;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.rx.RxHttpResponseCompat;
import com.example.wen.wenplay.common.rx.subscriber.ErrorHandleSubscriber;
import com.example.wen.wenplay.common.util.ACache;
import com.example.wen.wenplay.common.util.VerificationUtils;
import com.example.wen.wenplay.presenter.contract.LoginContract;
import com.hwangjr.rxbus.RxBus;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by wen on 2017/3/16.
 */

public class LoginPresenterImpl extends BasePresenter<LoginContract.ILoginModel,LoginContract.LoginView> {
    /**
     * @param model Presenter中使用的model
     * @param view  Presenter中使用的view
     */
    @Inject
    public LoginPresenterImpl(LoginContract.ILoginModel model, LoginContract.LoginView view) {
        super(model, view);
    }

    public void login(String phone, String pwd){
        if (!VerificationUtils.matcherPhoneNum(phone)){
            mView.checkAccountError();
        }else {
            mView.checkAccountSuccess();
        }

        mModel.login(phone,pwd).compose(RxHttpResponseCompat.<LoginBean>compatResult())
        .subscribe(new ErrorHandleSubscriber<LoginBean>(mContext) {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LoginBean loginBean) {
                    if (loginBean != null){
                        mView.loginSuccess(loginBean);
                        saveInfo(loginBean);
                        //发送事件
                       RxBus.get().post(loginBean.getUser());

                    }
            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void saveInfo(LoginBean loginBean){
        ACache aCache = ACache.get(mContext);
        aCache.put(Constant.TOKEN,loginBean.getToken());
        aCache.put(Constant.USER,loginBean.getUser());
    }
}
