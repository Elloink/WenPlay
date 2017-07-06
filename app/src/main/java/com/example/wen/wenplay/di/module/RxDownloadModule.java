package com.example.wen.wenplay.di.module;

import android.app.Application;
import android.os.Environment;

import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.util.ACache;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import zlc.season.rxdownload2.RxDownload;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

/**
 * Created by wen on 2017/5/16.
 */
@Module
public class RxDownloadModule {

    @Provides
    @Singleton
    public RxDownload provideRxDownload(Application application, Retrofit retrofit, File downDir){


        ACache.get(application).put(Constant.APK_DOWNLOAD_DIR,downDir.getPath());

        return RxDownload.getInstance(application)
                .defaultSavePath(downDir.getPath())
                .retrofit(retrofit)
                .maxDownloadNumber(10)
                .maxThread(10);
    }


    @Singleton
    @Provides
//    @FileType("download")
    File provideDownloadDir(){

        return Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);

    }
}
