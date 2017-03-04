package com.example.wen.wenplay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wen.wenplay.AppApplication;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.presenter.BasePresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wen on 2017/2/28.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    private Unbinder mUnBinder;
    private View mRootView;
    private AppApplication mAppApplication;

    @Inject
    T mPresenterImpl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutId(),container,false);
        mUnBinder =  ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAppApplication = (AppApplication) getActivity().getApplication();
        setUpFragmentComponent(mAppApplication.getAppComponent());

        init();

    }

    public abstract int setLayoutId();

    public abstract void setUpFragmentComponent(AppComponent appComponent);

    public abstract void init();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnBinder != Unbinder.EMPTY){
            mUnBinder.unbind();
        }
    }
}
