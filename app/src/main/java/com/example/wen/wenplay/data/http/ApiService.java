package com.example.wen.wenplay.data.http;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.Category;
import com.example.wen.wenplay.bean.IndexBean;
import com.example.wen.wenplay.bean.LoginBean;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.bean.SearchResult;
import com.example.wen.wenplay.bean.Subject;
import com.example.wen.wenplay.bean.SubjectDetail;
import com.example.wen.wenplay.bean.requestbean.LoginRequestBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wen on 2017/2/28.
 */

public interface ApiService {

    public static final String BASE_URL = "http://112.124.22.238:8081/course_api/cniaoplay/";

    @GET("featured2")
    public Observable<BaseBean<PageBean<AppInfo>>> getApps(@Query("p") String jsonParam);


    @GET("index")
    public  Observable<BaseBean<IndexBean>> index();

    @GET("toplist")
    public  Observable<BaseBean<PageBean<AppInfo>>> topList(@Query("page") int page);

    @GET("game")
    public  Observable<BaseBean<PageBean<AppInfo>>> games(@Query("page") int page);

    @POST("login")
    public Observable<BaseBean<LoginBean>> login(@Body LoginRequestBean loginRequestBean);

    @GET("category")
    public  Observable<BaseBean<List<Category>>> category();

    @GET("category/featured/{categoryid}")
    public  Observable<BaseBean<PageBean<AppInfo>>> getFeaturedAppByCategory(@Path("categoryid") int categoryid,@Query("page") int page);

    @GET("category/toplist/{categoryid}")
    public  Observable<BaseBean<PageBean<AppInfo>>> getTopListAppByCategory(@Path("categoryid") int categoryid,@Query("page") int page);

    @GET("category/newlist/{categoryid}")
    public  Observable<BaseBean<PageBean<AppInfo>>> getNewListAppByCategory(@Path("categoryid") int categoryid,@Query("page") int page);

    @GET("app/{id}")
    public Observable<BaseBean<AppInfo>> getAppDetail(@Path("id") int id);

    @GET("apps/updateinfo")
    Observable<BaseBean<List<AppInfo>>> getAppsUpdateinfo(@Query("packageName") String packageName,@Query("versionCode") String versionCode);

    @GET("subject/hot")
    Observable<BaseBean<PageBean<Subject>>> subjects(@Query("page") int page);

    @GET("subject/{id}")
    Observable<BaseBean<SubjectDetail>> subjectDetail(@Path("id") int id);


    @GET("search/suggest")
    Observable<BaseBean<List<String>>> searchSuggest(@Query("keyword") String keyword);


    @GET("search")
    Observable<BaseBean<SearchResult>> search(@Query("keyword") String keyword);

}
