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

public class GamesFragment extends BaseAppInfoFragment {

    @Override
    AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.Builder().showPosition(false).showBrief(true).showCategoryName(false).rxDownload(rxDownload).build();
    }

    @Override
    int type() {
        return AppInfoPresenterImpl.GAME;
    }

    @Override
    public void setUpFragmentComponent(AppComponent appComponent) {
       // DaggerAppInfoComponent.builder().appComponent(appComponent).appInfoModule(new AppInfoModule(this)).build().injectGamesFragment(this);
        DaggerAppInfoComponent.builder().appComponent(appComponent).appModelModule(new AppModelModule()).appInfoModule(new AppInfoModule(this)).build().injectGamesFragment(this);
    }
}
