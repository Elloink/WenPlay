package com.example.wen.wenplay.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.wen.wenplay.AppApplication;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.presenter.BasePresenter;
import com.example.wen.wenplay.ui.BaseView;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wen on 2017/2/28.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView{

    private Unbinder mUnBinder;
    protected AppApplication mAppApplication;

    @Inject
    T presenterImpl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));

        super.onCreate(savedInstanceState);

        setContentView(setLayoutId());

        mUnBinder = ButterKnife.bind(this);

        mAppApplication = (AppApplication) getApplication();

        setUpActivityComponent(mAppApplication.getAppComponent());

        init();
    }

    public abstract int setLayoutId();

    public abstract void setUpActivityComponent(AppComponent appComponent);

    public abstract void init();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != Unbinder.EMPTY){
            mUnBinder.unbind();
        }
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }
}
