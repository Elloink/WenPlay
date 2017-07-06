package com.example.wen.wenplay.ui.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.LoginBean;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.di.component.DaggerLoginComponent;
import com.example.wen.wenplay.di.module.LoginModule;
import com.example.wen.wenplay.presenter.LoginPresenterImpl;
import com.example.wen.wenplay.presenter.contract.LoginContract;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;


public class LoginActivity extends BaseActivity<LoginPresenterImpl> implements LoginContract.LoginView{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.phone_container)
    TextInputLayout mPhoneContainer;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.password_container)
    TextInputLayout mPasswordContainer;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_register)
    Button mBtnRegister;


    @Override
    public int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void setUpActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder().appComponent(appComponent).loginModule(new LoginModule(this)).build().inject(this);
    }

    @Override
    public void init() {

        initView();
    }

    private void initView() {
        final InitialValueObservable<CharSequence> phoneObservable = RxTextView.textChanges(mEtPhone);
        final InitialValueObservable<CharSequence> passwordObservable = RxTextView.textChanges(mEtPassword);

        Observable.combineLatest(phoneObservable, passwordObservable, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence phone, CharSequence password) throws Exception {

                return isPhoneAvailable(phone) && isPasswordAvailable(password);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                RxView.enabled(mBtnLogin).accept(aBoolean);
            }
        });
        
        RxView.clicks(mBtnLogin).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                presenterImpl.login(mEtPhone.getText().toString().trim(),mEtPassword.getText().toString().trim());
            }
        });

    }

    public boolean isPhoneAvailable(CharSequence phone){
        return phone.length() == 11;
    }

    public boolean isPasswordAvailable(CharSequence password){
        return password.length() >= 6;
    }

    @Override
    public void checkAccountError() {
        mPhoneContainer.setError("账号或密码错误");
    }

    @Override
    public void checkAccountSuccess() {
        mPhoneContainer.setError("");
        mPhoneContainer.setErrorEnabled(false);
    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        Toast.makeText(this,"Login success !",Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void dismissLoading() {

    }
}
