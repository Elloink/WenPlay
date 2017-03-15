package com.example.wen.wenplay.di.component;

import android.app.Application;

import com.example.wen.wenplay.AppApplication;
import com.example.wen.wenplay.common.rx.subscriber.ErrorHandler;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.di.module.AppModule;
import com.example.wen.wenplay.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wen on 2017/2/28.
 */
//由于引用的modules AppModule中的方法使用了@Singleton，因此此处必须加上@Singleton
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    //提供一个方法获取ApiService，会到相应的HttpModule寻找
    public ApiService getApiService();

    public Application getApplication();


}
