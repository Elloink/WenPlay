package com.example.wen.wenplay.di.module;

import android.app.Application;

import com.example.wen.wenplay.common.rx.subscriber.ErrorHandler;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 系统级别Module
 * Created by wen on 2017/2/28.
 */

@Module
public class AppModule {
    //Application 是系统级别的无法new出来，使用构造方法传入
    private Application mApplication;

    public AppModule(Application application){
        this.mApplication = application;
    }

    @Singleton
    @Provides
    public Application provideApplication(){
        return mApplication;
    }

    @Singleton
    @Provides
    public Gson provideGson(){
        return new Gson();
    }

}
