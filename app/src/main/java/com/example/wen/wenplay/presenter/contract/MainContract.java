package com.example.wen.wenplay.presenter.contract;


import android.content.Context;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.IndexBean;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.bean.requestbean.AppUpdateBean;
import com.example.wen.wenplay.common.apkparset.AndroidApk;
import com.example.wen.wenplay.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;


/**
 * 契约类
 * Created by wen on 2017/2/28.
 */

public class MainContract {
    //接口实现接口使用extends


    public interface IMainModel{
        Observable<BaseBean<List<AppInfo>>> getUpdateAppInfo(AppUpdateBean appUpdateBean);
        Observable<List<AndroidApk>> getInstalledAppsExceptSystemApp(Context context);
    }

    public interface MainView extends BaseView{

        void showNeedUpdateAppCount(int updateCount); //获取到数据后展示数据（初始化View）

        void permissionGranted();

        void permissionDenied();
    }



}
