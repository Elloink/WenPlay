package com.example.wen.wenplay.di.module;

import android.app.Application;

import com.example.wen.wenplay.common.http.CommonParamsInterceptor;
import com.example.wen.wenplay.common.rx.subscriber.ErrorHandler;
import com.example.wen.wenplay.data.http.ApiService;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络访问
 * Created by wen on 2017/2/28.
 */
@Module
public class HttpModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Application application, Gson gson){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();


     /*   if(BuildConfig.DEBUG){
            // log用拦截器
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder.addInterceptor(logging);

        }*/

        // 如果使用到HTTPS，我们需要创建SSLSocketFactory，并设置到client
//        SSLSocketFactory sslSocketFactory = null;
        return builder
                .addInterceptor(new CommonParamsInterceptor(application,gson))
                // 连接超时时间设置
                .connectTimeout(10, TimeUnit.SECONDS)
                // 读取超时时间设置
                .readTimeout(10, TimeUnit.SECONDS)

                .build();


    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
               // .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //1.0
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient);

        return builder.build();

    }

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }


}
