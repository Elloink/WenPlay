package com.example.wen.wenplay.ui.fragment;


import android.support.v7.widget.RecyclerView;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.di.component.DaggerAppManagerComponent;
import com.example.wen.wenplay.di.module.AppManagerModule;
import com.example.wen.wenplay.ui.adapter.AppInfoAdapter;

import java.util.List;

/**
 * Created by wen on 2017/5/19.
 */

public class UpdateFragment extends BaseAppManagerFragment {

 AppInfoAdapter mAdapter;

    @Override
    public void setUpFragmentComponent(AppComponent appComponent) {
        DaggerAppManagerComponent.builder().appComponent(appComponent).appManagerModule(new AppManagerModule(this)).build()
                .injectUpdateFragment(this);
    }

    @Override
    public void init() {
        super.init();

        mPresenterImpl.getUpdateApps();
    }

    @Override
    RecyclerView.Adapter buildAdapter() {
        mAdapter = AppInfoAdapter.Builder().showPosition(false).isUpdate(true).rxDownload(mPresenterImpl.getRxDownload()).build();

        return mAdapter;
    }

    @Override
    public void showUpdate(List<AppInfo> appInfoList) {
        mAdapter.addData(appInfoList);
    }
}
