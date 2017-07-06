package com.example.wen.wenplay.data;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.apkparset.AndroidApk;
import com.example.wen.wenplay.common.util.ACache;
import com.example.wen.wenplay.common.util.AppUtils;
import com.example.wen.wenplay.presenter.contract.AppManagerContract;


import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by wen on 2017/3/16.
 */

public class AppManagerModel implements AppManagerContract.IAppManagerModel {
    private RxDownload mRxDownload;
    private Context mContext;

    public AppManagerModel(Context context,RxDownload rxDownload){
        this.mContext = context;
        this.mRxDownload = rxDownload;
    }


    @Override
    public Observable<List<DownloadRecord>> getDownloadRecord() {
        //拿到所有的下载记录（包括已经下载完成的的），所以需要去除不需要的记录

        return  mRxDownload.getTotalDownloadRecords()
                .flatMap(new Function<List<DownloadRecord>, ObservableSource<List<DownloadRecord>>>() {
                    @Override
                    public ObservableSource<List<DownloadRecord>> apply(@NonNull List<DownloadRecord> downloadRecordList) throws Exception {

                        return  filterDownloadRecord(downloadRecordList);
                    }
                });
    }

    @Override
    public Observable<List<AndroidApk>> getLocalApks() {

      final String dir = ACache.get(mContext).getAsString(Constant.APK_DOWNLOAD_DIR);

        return Observable.create(new ObservableOnSubscribe<List<AndroidApk>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AndroidApk>> e) throws Exception {

                e.onNext(scanApks(dir));
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<List<AndroidApk>> getInstalled() {




        return Observable.create(new ObservableOnSubscribe<List<AndroidApk>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AndroidApk>> e) throws Exception {
                List<AndroidApk> installedApps = AppUtils.getInstalledApps(mContext);
                e.onNext(sortAndroidApk(installedApps));
                e.onComplete();
            }
        });
    }

    private List<AndroidApk> sortAndroidApk(List<AndroidApk> androidApks){
        List<AndroidApk> newList= new ArrayList<>(androidApks.size());
        List<AndroidApk> systemApk = new ArrayList<>();
        List<AndroidApk> thirdPartyApk = new ArrayList<>();
        for (AndroidApk apk : androidApks){
            if (apk.isSystem()){
                systemApk.add(apk);
            }else {
                thirdPartyApk.add(apk);
            }
        }

        newList.addAll(thirdPartyApk);

        newList.addAll(systemApk);

        return newList;
    }


    /**
     * 根据目录扫描下载的APK文件
     * @param dir apk下载目录
     * @return
     */
    private List<AndroidApk> scanApks(String dir) {

        File file = new File(dir);

        if(!file.isDirectory()){
            throw  new RuntimeException("is not Dir");
        }



        File[] apks =file.listFiles(new FileFilter(){ //列出目录中的所有文件，并过滤


            @Override
            public boolean accept(File f) {

                if(f.isDirectory()){ //该文件依然是目录，过滤
                    return  false;
                }

                return f.getName().endsWith(".apk"); //只需要以“.apk”结尾的文件
            }
        });


        List<AndroidApk>  androidApks = new ArrayList<>();

        for (File apk : apks){
            //将目录中的APK文件转化为AndroidApk对象
            AndroidApk androidApk = AndroidApk.read(mContext,apk.getPath());
            if(androidApk !=null)
                androidApks.add(androidApk);
        }

        return androidApks;
    }

    @Override
    public RxDownload getRxDownload() {
        return mRxDownload;
    }


    //过滤已经下载完成的记录
    private Observable<List<DownloadRecord>> filterDownloadRecord(List<DownloadRecord> downloadRecordList) {

        List<DownloadRecord> newDownloadRecordList = new ArrayList<>();
        for (DownloadRecord downloadRecord : downloadRecordList){
            if (DownloadFlag.COMPLETED != downloadRecord.getFlag()){
                newDownloadRecordList.add(downloadRecord);
            }
        }
        return Observable.just(newDownloadRecordList);
    }



}
