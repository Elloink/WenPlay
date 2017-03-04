package com.example.wen.wenplay.data;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.data.http.ApiService;
import retrofit2.Callback;


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
    public void getApps(Callback<PageBean<AppInfo>> callback){

     /*   HttpManager httpManager = new HttpManager();

        ApiService apiService = httpManager.getRetrofit(httpManager.getOkHttpClient()).create(ApiService.class);*/

        mApiService.getApps("{page:0}").enqueue(callback);

    }

}
