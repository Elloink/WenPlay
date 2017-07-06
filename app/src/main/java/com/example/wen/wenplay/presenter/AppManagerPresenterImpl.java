package com.example.wen.wenplay.presenter;

import android.text.TextUtils;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.LoginBean;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.apkparset.AndroidApk;
import com.example.wen.wenplay.common.rx.RxHttpResponseCompat;
import com.example.wen.wenplay.common.rx.subscriber.ErrorHandleSubscriber;
import com.example.wen.wenplay.common.rx.subscriber.SweetProgressDialogSubscriber;
import com.example.wen.wenplay.common.util.ACache;
import com.example.wen.wenplay.common.util.VerificationUtils;
import com.example.wen.wenplay.presenter.contract.AppManagerContract;
import com.example.wen.wenplay.presenter.contract.LoginContract;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hwangjr.rxbus.RxBus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by wen on 2017/3/16.
 */

public class AppManagerPresenterImpl extends BasePresenter<AppManagerContract.IAppManagerModel,AppManagerContract.AppManagerView> {
    /**
     * @param model Presenter中使用的model
     * @param view  Presenter中使用的view
     */
    @Inject
    public AppManagerPresenterImpl(AppManagerContract.IAppManagerModel model, AppManagerContract.AppManagerView view) {
        super(model, view);
    }


    public void getDownloadRecord(){
        mModel.getDownloadRecord().subscribe(new SweetProgressDialogSubscriber<List<DownloadRecord>>(mContext,mView) {
            @Override
            public void onNext(List<DownloadRecord> downloadRecordList) {
                if (downloadRecordList != null){
                    mView.showRecord(downloadRecordList);
                }
            }
        });
    }

    public void getLocalApks(){
        mModel.getLocalApks().subscribe(new SweetProgressDialogSubscriber<List<AndroidApk>>(mContext,mView){

            @Override
            public void onNext(List<AndroidApk> androidApks) {
                mView.showDone(androidApks);
            }
        });
    }

    public void getInstalledApps(){
        mModel.getInstalled().subscribe(new SweetProgressDialogSubscriber<List<AndroidApk>>(mContext,mView) {
            @Override
            public void onNext(List<AndroidApk> androidApks) {

               // mView.showInstalled(androidApks);
                mView.showInstalled(androidApks);
            }
        });

    }

    public void getUpdateApps(){

        String appInfoString = ACache.get(mContext).getAsString(Constant.UPDATE_APPINFO_KEY);

        if (! TextUtils.isEmpty(appInfoString)) {
            Gson gson = new Gson();

           List<AppInfo> appInfoList =  gson.fromJson(appInfoString,new TypeToken<List<AppInfo>>(){}.getType());


            Observable.just(appInfoList).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SweetProgressDialogSubscriber<List<AppInfo>>(mContext,mView) {
                        @Override
                        public void onNext(List<AppInfo> appInfoList) {
                            mView.showUpdate(appInfoList);
                        }
                    });

        }


    }




    public RxDownload getRxDownload(){
        return mModel.getRxDownload();
    }
}
