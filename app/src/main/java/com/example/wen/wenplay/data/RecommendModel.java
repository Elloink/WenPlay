package com.example.wen.wenplay.data;

import android.util.Log;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.IndexBean;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.data.http.ApiService;

import io.reactivex.Observable;


/**
 * RecommendModel 负责获取RecommendFragment需要的数据
 * Created by wen on 2017/2/28.
 */

public class RecommendModel {

    private ApiService mApiService;

    public RecommendModel(ApiService apiService){
        this.mApiService = apiService;
    }

    //PageBean<AppInfo>
    public Observable<BaseBean<PageBean<AppInfo>>> getApps(){

     /*   HttpManager httpManager = new HttpManager();

        ApiService apiService = httpManager.getRetrofit(httpManager.getOkHttpClient()).create(ApiService.class);*/

        Log.d("RecommendModel",mApiService.getApps("{page:0}").toString());
        return mApiService.getApps("{page:0}");

    }

    public Observable<BaseBean<IndexBean>> index(){
        return mApiService.index();
    }


}
