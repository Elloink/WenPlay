package com.example.wen.wenplay.di.module;

import com.example.wen.wenplay.data.AppInfoModel;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.presenter.AppInfoPresenterImpl;
import com.example.wen.wenplay.presenter.contract.AppInfoContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/2/28.
 */
@Module
public class AppModelModule {

    @Provides
    public AppInfoModel provideAppInfoModel(ApiService apiService){

        return new AppInfoModel(apiService);
    }

}
