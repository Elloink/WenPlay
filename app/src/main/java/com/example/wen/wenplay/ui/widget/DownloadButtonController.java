package com.example.wen.wenplay.ui.widget;

import android.Manifest;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.AppDownloadInfo;
import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.User;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.rx.RxHttpResponseCompat;
import com.example.wen.wenplay.common.util.ACache;
import com.example.wen.wenplay.common.util.AppUtils;
import com.example.wen.wenplay.common.util.PackageUtils;
import com.example.wen.wenplay.common.util.PermissionUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.Path;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadBean;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadRecord;


/**
 *
 */

public class DownloadButtonController {





    private RxDownload mRxDownload ;
    private final String APP_SUFFIX = ".apk";


   // private String mDownloadDir; // 文件下载的目录


    private Api api;

    public DownloadButtonController(RxDownload rxDownload){
        this.mRxDownload = rxDownload;

        if (mRxDownload != null){
            api =  mRxDownload.getRetrofit().create(Api.class);
        }
    }

    public  void  handClick(final DownloadProgressButton btn,  DownloadRecord downloadRecord){

        AppInfo appInfo = downloadRecord2AppInfo(downloadRecord);
        receiveDownloadStatus(downloadRecord.getUrl()).subscribe(new DownloadConsumer(btn,appInfo));

    }

    public  void  handClick(final DownloadProgressButton btn, final AppInfo appInfo){

        if (api == null){
            return;
        }

        isAppInstalled(btn.getContext(),appInfo)

                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent event)
                            throws Exception {

                        if(DownloadFlag.UN_INSTALL == event.getFlag()){
                            //如果應用未安裝，判斷apk文件是否存在
                            return  isApkFileExist(btn.getContext(),appInfo);

                        }else if(DownloadFlag.INSTALLED ==  event.getFlag()){
                            //应用已安装，判断是否需要更新
                           return isNeedToUpdate(btn.getContext(),appInfo).flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                               @Override
                               public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent downloadEvent) throws Exception {

                                   if (DownloadFlag.NEED_UPDATE == downloadEvent.getFlag()){
                                       //需要更新，判断更新APK文件是否存在
                                       return isApkFileExist(btn.getContext(),appInfo);

                                   }else {
                                       //應用已安裝并且不需要更新，返回本身
                                       return Observable.just(downloadEvent);
                                   }


                               }
                           });





                        }else {
                            //應用已安裝并且不需要更新，返回本身
                            return Observable.just(event);
                        }
                    }
                })
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent event) throws Exception {


                     /*   if (DownloadFlag.NEED_UPDATE == event.getFlag()){
                            //需要更新



                        }*/




                        if(DownloadFlag.FILE_EXIST == event.getFlag()){
                            //文件存在，判斷是否正在下載（修改下載進度），還是下載暫停（繼續），或者是已經下載完成（安裝）
                            return  getAppDownloadInfo(appInfo).flatMap(new Function<AppDownloadInfo, ObservableSource<DownloadEvent>>() {
                                @Override
                                public ObservableSource<DownloadEvent> apply(@NonNull AppDownloadInfo appDownloadInfo) throws Exception {

                                    //保存AppDownloadInfo，用來處理暫停
                                    appInfo.setAppDownloadInfo(appDownloadInfo);



                                    //根據mRxDownload.receiveDownloadStatus方法獲取下載文件的狀態
                                    return receiveDownloadStatus(appDownloadInfo.getDownloadUrl());
                                }
                            });

                        }

                        //文件不存在，返回本身
                        return Observable.just(event);
                    }
                })
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DownloadConsumer(btn,appInfo));

    }


    private boolean isLogin(Context context){

        Object objUser= ACache.get(context).getAsObject(Constant.USER);

        if(objUser == null){

          return false;

        }
        else{
            return true;
        }


    }


    public void bindClick(final DownloadProgressButton btn, final AppInfo appInfo) {
        RxView.clicks(btn).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {


                if (isLogin(btn.getContext())){

                    int flag = (int) btn.getTag(R.id.tag_apk_flag);

                    switch (flag){

                        case DownloadFlag.INSTALLED: //已安裝，點擊運行應用
                            runApp(btn.getContext(),appInfo);
                            break;

                        case DownloadFlag.NEED_UPDATE: //需要更新

                            startDownload(btn,appInfo);

                        case DownloadFlag.STARTED://正在開始，點擊后暫停下載
                            pausedDownload(appInfo);

                            break;

                        case DownloadFlag.PAUSED: //暫停,點擊后繼續下載
                        case DownloadFlag.NORMAL: //未安裝，點擊開始下載文件
                            startDownload(btn,appInfo);
                            break;

                        case DownloadFlag.COMPLETED: //下載完成，點擊安裝
                            installApp(btn.getContext(),appInfo);
                            break;


                    }
                }else {
                    Toast.makeText(btn.getContext(),"登录后才能开始下载哦",Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

   /* private void updateApp(DownloadProgressButton btn, AppInfo appInfo) {
        startDownload(btn,appInfo);
    }*/

    //安裝Apk
    private void installApp(Context context,AppInfo appInfo) {

      //  String downloadDir = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR);

       // String path = downloadDir + File.separator + appInfo.getReleaseKeyHash()+APP_SUFFIX;

        String path = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR) + File.separator + appInfo.getReleaseKeyHash();

       // Log.d("DOWNLOAD","downloadDir:"+downloadDir+"\n"+"path:"+path);

       /* Toast.makeText(context,"开始安装"+appInfo.getDisplayName()+"\n安装路径："+path,Toast.LENGTH_SHORT).show();*/

      // AppUtils.installApk(context,path);
       PackageUtils.install(context,path);
    }

    //開始下載
    private void startDownload(final DownloadProgressButton btn, final AppInfo appInfo) {
        AppDownloadInfo appDownloadInfo = appInfo.getAppDownloadInfo();
        if (appDownloadInfo == null){

            //第一次進來開始下載時appDownloadInfo為空
            getAppDownloadInfo(appInfo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AppDownloadInfo>() {
                        @Override
                        public void accept(@NonNull AppDownloadInfo appDownloadInfo) throws Exception {
                            appInfo.setAppDownloadInfo(appDownloadInfo);

                            //下載
                          download(btn,appInfo);

                        }
                    });

        }else {
            //繼續下載
            download(btn,appInfo);
        }

    }

    //執行下載
    public void download(final DownloadProgressButton btn, final AppInfo appInfo){

        PermissionUtil.requestPermission(btn.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionUtil.PermissionCallBack() {
            @Override
            public void permissionGranted(Observable<Boolean> requestObservable) {

                //mRxDownload.serviceDownload(appInfo.getAppDownloadInfo().getDownloadUrl(),appInfo.getReleaseKeyHash()+APP_SUFFIX).subscribe();
                mRxDownload.serviceDownload(appInfo2DownloadBean(appInfo)).subscribe();

                //監聽下載的狀態
                receiveDownloadStatus(appInfo.getAppDownloadInfo().getDownloadUrl()).subscribe(new DownloadConsumer(btn,appInfo));

            }

            @Override
            public void permissionDenied(Observable<Boolean> requestObservable) {
                Toast.makeText(btn.getContext(),"您已拒绝授权 ！",Toast.LENGTH_SHORT).show();
            }
        });



    }

    //AppInfo转为 DownloadBean
    public DownloadBean appInfo2DownloadBean(AppInfo appInfo){
        DownloadBean downloadBean = new DownloadBean();
        downloadBean.setExtra1(appInfo.getId()+"");
        downloadBean.setExtra2(appInfo.getIcon());
        downloadBean.setExtra3(appInfo.getDisplayName());
        downloadBean.setExtra4(appInfo.getPackageName());
        downloadBean.setExtra5(appInfo.getReleaseKeyHash());
        downloadBean.setUrl(appInfo.getAppDownloadInfo().getDownloadUrl());
        downloadBean.setSaveName(appInfo.getReleaseKeyHash()+APP_SUFFIX);
        return downloadBean;
    }

    //DownloadRecord 转为 AppInfo
    public AppInfo downloadRecord2AppInfo(DownloadRecord downloadRecord){

        AppInfo appInfo = new AppInfo();
        appInfo.setId(Integer.parseInt(downloadRecord.getExtra1()));
        appInfo.setIcon(downloadRecord.getExtra2());
        appInfo.setDisplayName(downloadRecord.getExtra3());
        appInfo.setPackageName(downloadRecord.getExtra4());
        appInfo.setReleaseKeyHash(downloadRecord.getExtra5());
        appInfo.setFlag(downloadRecord.getFlag()); //记录下载状态
        AppDownloadInfo downloadInfo = new AppDownloadInfo();
        downloadInfo.setDownloadUrl(downloadRecord.getUrl());
        appInfo.setAppDownloadInfo(downloadInfo);

        return appInfo;
    }

  /*  //获取下载记录信息（解决recyclerView复用）
    public Observable<DownloadRecord> getFlag(AppInfo appInfo){
        if (appInfo != null && appInfo.getAppDownloadInfo() != null) {
            return mRxDownload.getDownloadRecord(appInfo.getAppDownloadInfo().getDownloadUrl());
        }else {
            return Observable.empty();
        }
    }*/


    //暫停下載
    private void pausedDownload(AppInfo appInfo) {
        //能夠暫停下載，說明AppDownloadInfo一定存在,利用rxDownload的方法暫停下載，注意必須訂閱
        mRxDownload.pauseServiceDownload(appInfo.getAppDownloadInfo().getDownloadUrl()).subscribe();

    }

    //運行APP
    private void runApp(Context context, AppInfo appInfo) {
        AppUtils.runApp(context,appInfo.getPackageName());
    }


    //根據該應用是否安裝設置DownloadEvent的flag
    public Observable<DownloadEvent> isAppInstalled(Context context,AppInfo appInfo){

        DownloadEvent event = new DownloadEvent();

        event.setFlag(AppUtils.isInstalled(context,appInfo.getPackageName())? DownloadFlag.INSTALLED:DownloadFlag.UN_INSTALL);

        return  Observable.just(event);

    }

    private Observable<DownloadEvent> isNeedToUpdate(Context context,AppInfo appInfo) {

        DownloadEvent event = new DownloadEvent();
       event.setFlag(appInfo.isUpdate()==true?DownloadFlag.NEED_UPDATE:DownloadFlag.INSTALLED);

      /*  String s = ACache.get(context).getAsString(Constant.UPDATE_APPINFO_KEY);

        Log.d("DownloadButton","获取的json:"+s);

        if (!TextUtils.isEmpty(s)){

            Gson gson = new Gson();

            List<AppInfo> updateList =  gson.fromJson(s,new TypeToken<List<AppInfo>>(){}.getType());

            if (updateList != null){
                Log.d("DownloadButton","updateList不为空:appInfo.getVersionCode()"+appInfo.getVersionCode());
                for (AppInfo info:updateList) {
                    if (info.getVersionCode() == appInfo.getVersionCode()){
                        Log.d("DownloadButton","info.getVersionCode():"+info.getVersionCode());
                        event.setFlag(DownloadFlag.NEED_UPDATE);
                    }else {
                        event.setFlag(DownloadFlag.INSTALLED);
                    }
                }



            }else {

            }

        }else {

            event.setFlag(DownloadFlag.INSTALLED);
        }*/


        return Observable.just(event);
    }


    //根據該應用apk文件是否存在設置DownloadEvent的flag
    public  Observable<DownloadEvent> isApkFileExist(Context context , AppInfo appInfo){


        //String downloadDir = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR);

        //下載地址約束
       // String path = downloadDir + File.separator + appInfo.getReleaseKeyHash()+APP_SUFFIX;

        String path = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR) + File.separator + appInfo.getReleaseKeyHash();
        File file = new File(path);


        DownloadEvent event = new DownloadEvent();



        if (appInfo.isUpdate()){
            event.setFlag(file.exists() ? DownloadFlag.FILE_EXIST : DownloadFlag.NEED_UPDATE);
        }else {
            event.setFlag(file.exists() ? DownloadFlag.FILE_EXIST : DownloadFlag.NORMAL);
        }




        return  Observable.just(event);


    }




    //獲取下載文件的狀態
    public Observable<DownloadEvent> receiveDownloadStatus(String url){

       return  mRxDownload.receiveDownloadStatus(url);
    }




    public Observable<AppDownloadInfo> getAppDownloadInfo(AppInfo appInfo){

        return  api.getAppDownloadInfo(appInfo.getId()).compose(RxHttpResponseCompat.<AppDownloadInfo>compatResult());
    }


    class DownloadConsumer implements Consumer<DownloadEvent>{

        private DownloadProgressButton btn;
        private AppInfo appInfo;

        public DownloadConsumer(DownloadProgressButton btn,AppInfo appInfo){
            this.btn = btn;
            this.appInfo = appInfo;
        }

        @Override
        public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
            //根據狀態處理按鈕的邏輯
            int flag = downloadEvent.getFlag();

            //每次回調的時候綁定flag
            btn.setTag(R.id.tag_apk_flag,flag);

            //綁定點擊事件，處理每次的點擊
            bindClick(btn,appInfo);

      /*      //处理recyclerView复用问题
            appInfo.setDownloadEvent(downloadEvent);*/
            appInfo.setFlag(flag);

            switch (flag){

                case DownloadFlag.INSTALLED:
                    btn.setText("运行");
                    break;

                case DownloadFlag.NEED_UPDATE:
                    btn.setText("更新");
                    break;

                case DownloadFlag.NORMAL:
                    btn.download();
                    break;


                case DownloadFlag.STARTED:
                    btn.setProgress((int) downloadEvent.getDownloadStatus().getPercentNumber());
                    appInfo.setProgress((int) downloadEvent.getDownloadStatus().getPercentNumber());
                    break;

                case DownloadFlag.PAUSED:
                    btn.setProgress((int) downloadEvent.getDownloadStatus().getPercentNumber());
                    appInfo.setProgress((int) downloadEvent.getDownloadStatus().getPercentNumber());
                    btn.paused();
                    break;


                case DownloadFlag.COMPLETED: //已完成
                    btn.setText("安装");
                    //installApp(btn.getContext(),mAppInfo);
                    break;
                case DownloadFlag.FAILED://下载失败
                    btn.setText("失败");
                    break;
                case DownloadFlag.DELETED: //已删除

                    break;


            }
        }
    }


    interface  Api{

        @GET("download/{id}")
        Observable<BaseBean<AppDownloadInfo>> getAppDownloadInfo(@Path("id") int id);
    }

}
