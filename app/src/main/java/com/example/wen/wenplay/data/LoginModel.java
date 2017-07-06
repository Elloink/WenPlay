package com.example.wen.wenplay.data;

import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.LoginBean;
import com.example.wen.wenplay.bean.requestbean.LoginRequestBean;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.presenter.contract.LoginContract;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/3/16.
 */

public class LoginModel implements LoginContract.ILoginModel {
    private ApiService mApiService;

    public LoginModel(ApiService apiService){
        this.mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<LoginBean>> login(String phone, String pwd) {
        LoginRequestBean loginRequestBean = new LoginRequestBean();
        loginRequestBean.setEmail(phone);
        //可加入密码加密操作
        loginRequestBean.setPassword(pwd);

        return mApiService.login(loginRequestBean);
    }
}
