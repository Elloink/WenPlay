package com.example.wen.wenplay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wen.wenplay.R;
import com.example.wen.wenplay.common.apkparset.AndroidApk;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.di.component.DaggerAppManagerComponent;
import com.example.wen.wenplay.di.module.AppManagerModule;
import com.example.wen.wenplay.ui.adapter.AndroidApkAdapter;

import java.util.List;

/**
 * Created by wen on 2017/5/19.
 */

public class InstalledFragment extends BaseAppManagerFragment {


    private AndroidApkAdapter mAdapter;

    @Override
    public void init() {
        super.init();
        mPresenterImpl.getInstalledApps();
    }

    @Override
    public void setUpFragmentComponent(AppComponent appComponent) {
        DaggerAppManagerComponent.builder().appComponent(appComponent).appManagerModule(new AppManagerModule(this)).build()
                .injectInstalledFragment(this);
    }

    @Override
    RecyclerView.Adapter buildAdapter() {

        mAdapter = new AndroidApkAdapter(AndroidApkAdapter.FLAG_APP);
        return mAdapter;
    }

    @Override
    public void showInstalled(List<AndroidApk> androidApkList) {
        mAdapter.addData(androidApkList);
    }
}
