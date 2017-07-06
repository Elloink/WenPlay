package com.example.wen.wenplay.data;

import android.content.Context;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.requestbean.AppUpdateBean;
import com.example.wen.wenplay.common.apkparset.AndroidApk;
import com.example.wen.wenplay.common.util.AppUtils;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.presenter.contract.MainContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/5/24.
 */

public class MainModel implements MainContract.IMainModel {

    private ApiService mApiService;

    public MainModel(ApiService apiService){
        this.mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<List<AppInfo>>> getUpdateAppInfo(AppUpdateBean appUpdateBean) {
        return mApiService.getAppsUpdateinfo(appUpdateBean.getPackageName(),appUpdateBean.getVersionCode());
    }


    //获取手机中所有的第三方应用
    public Observable<List<AndroidApk>> getInstalledAppsExceptSystemApp(Context context){
        List<AndroidApk> installedApps = AppUtils.getInstalledApps(context);
        List<AndroidApk> thirdPartyApps = new ArrayList<>();

        for (AndroidApk androidApk : installedApps){
            if (!androidApk.isSystem()){
                thirdPartyApps.add(androidApk);
            }
        }

        return Observable.just(thirdPartyApps);
    }

}
