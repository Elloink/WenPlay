package com.example.wen.wenplay.presenter;

import android.Manifest;
import android.content.Context;
import android.widget.Toast;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.requestbean.AppUpdateBean;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.apkparset.AndroidApk;
import com.example.wen.wenplay.common.rx.RxHttpResponseCompat;
import com.example.wen.wenplay.common.rx.subscriber.SweetProgressDialogSubscriber;
import com.example.wen.wenplay.common.util.ACache;
import com.example.wen.wenplay.common.util.JsonUtils;
import com.example.wen.wenplay.common.util.PermissionUtil;
import com.example.wen.wenplay.presenter.BasePresenter;
import com.example.wen.wenplay.presenter.contract.MainContract;
import com.example.wen.wenplay.ui.activity.MainActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wen on 2017/5/24.
 */

public class MainPresenterImpl extends BasePresenter<MainContract.IMainModel,MainContract.MainView> {

    @Inject
    public MainPresenterImpl(MainContract.IMainModel model, MainContract.MainView view) {
        super(model, view);
    }


    public void requestPermission(){
        PermissionUtil.requestPermission(mContext, Manifest.permission.READ_PHONE_STATE, new PermissionUtil.PermissionCallBack() {
            @Override
            public void permissionGranted(Observable<Boolean> requestObservable) {
                mView.permissionGranted();
            }

            @Override
            public void permissionDenied(Observable<Boolean> requestObservable) {
                mView.permissionDenied();
            }
        });
    }


    public void getUpdateAppInfo(){

        buildAppUpdateBean().flatMap(new Function<AppUpdateBean, ObservableSource<List<AppInfo>>>() {
            @Override
            public ObservableSource<List<AppInfo>> apply(@NonNull AppUpdateBean appUpdateBean) throws Exception {

               return mModel.getUpdateAppInfo(appUpdateBean).compose(RxHttpResponseCompat.<List<AppInfo>>compatResult());

            }
        }).subscribe(new SweetProgressDialogSubscriber<List<AppInfo>>(mContext,mView) {
            @Override
            public void onNext(List<AppInfo> appInfoList) {
                if(appInfoList !=null){

                    ACache.get(mContext).put(Constant.UPDATE_APPINFO_KEY, JsonUtils.toJson(appInfoList));
                }

                mView.showNeedUpdateAppCount(appInfoList==null?0:appInfoList.size());
            }
        });


    }



    private Observable<AppUpdateBean> buildAppUpdateBean() {

       return mModel.getInstalledAppsExceptSystemApp(mContext).flatMap(new Function<List<AndroidApk>, ObservableSource<AppUpdateBean>>() {
            @Override
            public ObservableSource<AppUpdateBean> apply(@NonNull List<AndroidApk> androidApks) throws Exception {

                AppUpdateBean appUpdateBean = new AppUpdateBean();

                StringBuilder packageName = new StringBuilder();
                StringBuilder versionCode = new StringBuilder();
                for (AndroidApk androidApk : androidApks){
                    packageName.append(androidApk.getPackageName()).append(",");
                    versionCode.append(androidApk.getAppVersionCode()).append(",");
                }
                appUpdateBean.setPackageName(packageName.toString());
                appUpdateBean.setVersionCode(versionCode.toString());
                return Observable.just(appUpdateBean);
            }
        });



    }
}
