package com.example.wen.wenplay.data.http;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.IndexBean;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.bean.requestbean.LoginRequestBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wen on 2017/2/28.
 */

public interface ApiService {

    public static final String BASE_URL = "http://112.124.22.238:8081/course_api/cniaoplay/";

    @GET("featured2")
    public Observable<BaseBean<PageBean<AppInfo>>> getApps(@Query("p") String jsonParam);


    //{"phone":"","password":""}
    @POST("login")
    public Observable<BaseBean> login(@Body LoginRequestBean bean);



    @FormUrlEncoded // FormBody
    @POST("login")
    public   void login2(@Field("phone") String phone);

    @GET("index")
    public  Observable<BaseBean<IndexBean>> index();

    @GET("toplist")
    public  Observable<BaseBean<PageBean<AppInfo>>> topList(@Query("page") int page);

    @GET("game")
    public  Observable<BaseBean<PageBean<AppInfo>>> games(@Query("page") int page);

}
