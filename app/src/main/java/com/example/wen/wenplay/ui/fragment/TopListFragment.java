package com.example.wen.wenplay.ui.fragment;


import com.example.wen.wenplay.di.component.AppComponent;

import com.example.wen.wenplay.di.component.DaggerAppInfoComponent;
import com.example.wen.wenplay.di.module.AppInfoModule;
import com.example.wen.wenplay.di.module.AppModelModule;
import com.example.wen.wenplay.presenter.AppInfoPresenterImpl;
import com.example.wen.wenplay.ui.adapter.AppInfoAdapter;


/**
 * Created by wen on 2017/2/27.
 */

public class TopListFragment extends BaseAppInfoFragment{


    @Override
    public void setUpFragmentComponent(AppComponent appComponent) {
        DaggerAppInfoComponent.builder().appComponent(appComponent).appModelModule(new AppModelModule()).appInfoModule(new AppInfoModule(this)).build().injectTopListFragment(this);
    }


    @Override
    AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.Builder().showPosition(true).showBrief(false).showCategoryName(true).rxDownload(rxDownload).build();
    }

    @Override
    int type() {
        return AppInfoPresenterImpl.TOP_LIST;
    }

}
