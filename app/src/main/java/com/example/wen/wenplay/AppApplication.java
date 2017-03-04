package com.example.wen.wenplay;

import android.app.Application;
import android.content.Context;

import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.di.component.DaggerAppComponent;
import com.example.wen.wenplay.di.module.AppModule;
import com.example.wen.wenplay.di.module.HttpModule;

/**
 * Created by wen on 2017/2/28.
 */

public class AppApplication extends Application {

    private AppComponent mAppComponent;

    public static AppApplication get(Context context){
        return (AppApplication) context.getApplicationContext();
    }

    public  AppComponent getAppComponent(){
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

       /* mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();*/

        mAppComponent =  DaggerAppComponent.builder().appModule(new AppModule(this)).httpModule(new HttpModule()).build();
    }
}
