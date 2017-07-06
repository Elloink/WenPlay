package com.example.wen.wenplay.ui.fragment;

import android.content.Context;
import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wen.wenplay.common.apkparset.AndroidApk;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.di.component.DaggerAppManagerComponent;
import com.example.wen.wenplay.di.module.AppManagerModule;
import com.example.wen.wenplay.ui.adapter.AndroidApkAdapter;

import java.util.List;

/**
 * Created by wen on 2017/5/19.
 */

public class DoneFragment extends BaseAppManagerFragment {

    private AndroidApkAdapter mAdapter;

    @Override
    BaseQuickAdapter buildAdapter() {

        mAdapter = new AndroidApkAdapter(AndroidApkAdapter.FLAG_APK);
        return mAdapter;
    }

    @Override
    public void setUpFragmentComponent(AppComponent appComponent) {
        DaggerAppManagerComponent.builder().appComponent(appComponent).appManagerModule(new AppManagerModule(this)).build().injectDoneFragment(this);
    }

    @Override
    public void init() {
        super.init();

        mPresenterImpl.getLocalApks();
    }

    @Override
    public void showDone(List<AndroidApk> androidApkList) {
        mAdapter.addData(androidApkList);
    }
}
