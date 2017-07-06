package com.example.wen.wenplay.di.module;

import android.app.Application;

import com.example.wen.wenplay.data.AppManagerModel;
import com.example.wen.wenplay.presenter.AppManagerPresenterImpl;
import com.example.wen.wenplay.presenter.contract.AppManagerContract;

import dagger.Module;
import dagger.Provides;
import zlc.season.rxdownload2.RxDownload;

/**
 * Created by wen on 2017/5/20.
 */
@Module
public class AppManagerModule {

    private AppManagerContract.AppManagerView mAppManagerView;

    public AppManagerModule(AppManagerContract.AppManagerView appManagerView){
        this.mAppManagerView = appManagerView;
    }

    @Provides
    public AppManagerContract.IAppManagerModel providerAppManagerModel(Application application, RxDownload rxDownload){
        return new AppManagerModel(application,rxDownload);
    }

    @Provides
    public AppManagerPresenterImpl providerAppManagerPresenterImpl(AppManagerContract.IAppManagerModel managerModel, AppManagerContract.AppManagerView appManagerView){
        return new AppManagerPresenterImpl(managerModel,appManagerView);
    }

    @Provides
    public AppManagerContract.AppManagerView providerAppManagerView(){
        return mAppManagerView;
    }

}
