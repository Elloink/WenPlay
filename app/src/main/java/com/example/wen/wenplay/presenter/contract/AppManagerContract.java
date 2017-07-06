package com.example.wen.wenplay.presenter.contract;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.LoginBean;
import com.example.wen.wenplay.common.apkparset.AndroidApk;
import com.example.wen.wenplay.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;


/**
 * Created by wen on 2017/3/16.
 */

public interface AppManagerContract {

    public interface IAppManagerModel{

        Observable<List<DownloadRecord>> getDownloadRecord();

        Observable<List<AndroidApk>> getLocalApks();

        Observable<List<AndroidApk>> getInstalled();

        RxDownload getRxDownload();

    }

    public interface AppManagerView extends BaseView{

        public void showRecord(List<DownloadRecord> downloadRecordList);

        public void showDone(List<AndroidApk> androidApkList);
        public void showInstalled(List<AndroidApk> androidApkList);

        public void showUpdate(List<AppInfo> appInfoList);

    }

}
