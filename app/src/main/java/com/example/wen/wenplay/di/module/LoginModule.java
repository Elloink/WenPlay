package com.example.wen.wenplay.di.module;


import com.example.wen.wenplay.data.LoginModel;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.presenter.LoginPresenterImpl;
import com.example.wen.wenplay.presenter.contract.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/2/28.
 */
@Module
public class LoginModule {

    private LoginContract.LoginView mLoginView;

    public LoginModule(LoginContract.LoginView loginView){
        this.mLoginView = loginView;
    }

    //需要的参数会从provides方法中找
    @Provides
   public LoginPresenterImpl provideLoginPresenterImpl(LoginContract.ILoginModel iLoginModel, LoginContract.LoginView loginView){
        return new LoginPresenterImpl(iLoginModel,loginView);
    }


    @Provides
    public LoginContract.LoginView provideLoginView(){
        return mLoginView;
    }

    @Provides
    public LoginContract.ILoginModel provideILoginModel(ApiService apiService){
        //由于此处参数ApiService 在HttpModule提供，因此需要在RecommendComponent中添加依赖
        return new LoginModel(apiService);
    }
}
