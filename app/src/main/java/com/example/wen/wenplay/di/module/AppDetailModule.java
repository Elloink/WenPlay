package com.example.wen.wenplay.di.module;

import com.example.wen.wenplay.data.AppInfoModel;
import com.example.wen.wenplay.presenter.AppDetailPresenterImpl;
import com.example.wen.wenplay.presenter.contract.AppInfoContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/4/18.
 */
@Module(includes = {AppModelModule.class})
public class AppDetailModule {
    private AppInfoContract.AppDetailView mAppDetailView;

    //构造方法传入
    public AppDetailModule(AppInfoContract.AppDetailView appDetailView){
        this.mAppDetailView = appDetailView;
    }

    //需要的参数会从provides方法中找
    @Provides
    public AppDetailPresenterImpl provideAppDetailPresenterImpl(AppInfoModel appInfoModel, AppInfoContract.AppDetailView appDetailView){
        return new AppDetailPresenterImpl(appInfoModel, appDetailView);
    }


    @Provides
    public AppInfoContract.AppDetailView provideAppDetailView(){
        return mAppDetailView;
    }
}
