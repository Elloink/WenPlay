package com.example.wen.wenplay.presenter.contract;

import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.LoginBean;
import com.example.wen.wenplay.ui.BaseView;

import io.reactivex.Observable;


/**
 * Created by wen on 2017/3/16.
 */

public interface LoginContract {

    public interface ILoginModel{

        Observable<BaseBean<LoginBean>> login(String phone, String pwd);

    }

    public interface LoginView extends BaseView{

        void checkAccountError();
        void checkAccountSuccess();

        void loginSuccess(LoginBean loginBean);
      //  void loginError(String errorMsg); //在基类错误处理中处理了，若有业务逻辑上的继续处理时加上该方法
    }
}
