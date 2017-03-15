package com.example.wen.wenplay.data;

import android.util.Log;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.IndexBean;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.data.http.ApiService;

import io.reactivex.Observable;


/**
 * AppInfoModel 负责获取RecommendFragment需要的数据
 * Created by wen on 2017/2/28.
 */

public class AppInfoModel {

    private ApiService mApiService;

    public AppInfoModel(ApiService apiService){
        this.mApiService = apiService;
    }

    //PageBean<AppInfo>
    public Observable<BaseBean<PageBean<AppInfo>>> getApps(){

     /*   HttpManager httpManager = new HttpManager();

        ApiService apiService = httpManager.getRetrofit(httpManager.getOkHttpClient()).create(ApiService.class);*/

        Log.d("AppInfoModel",mApiService.getApps("{page:0}").toString());
        return mApiService.getApps("{page:0}");

    }

    public Observable<BaseBean<IndexBean>> index(){
        return mApiService.index();
    }

    public Observable<BaseBean<PageBean<AppInfo>>> topList(int page){
        return mApiService.topList(page);
    }

    public Observable<BaseBean<PageBean<AppInfo>>> games(int page){
        return mApiService.games(page);
    }

}
