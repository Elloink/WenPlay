package com.example.wen.wenplay.common.util;

import android.app.Activity;
import android.content.Context;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wen on 2017/3/18.
 */

public class PermissionUtil {



    public static Observable<Boolean> requestPermission(Activity activity, String permission){
        RxPermissions rxPermissions =  RxPermissions.getInstance(activity);
        return rxPermissions.request(permission);
    }

    public static void requestPermission(Context activity, final String permission, final PermissionCallBack permissionCallBack){
        RxPermissions rxPermissions = RxPermissions.getInstance(activity);
        final Observable<Boolean> request = rxPermissions.request(permission);

        request.subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean value) {
                if (value){
                    permissionCallBack.permissionGranted(request);
                }else {
                    permissionCallBack.permissionDenied(request);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public static ObservableTransformer<Object, Boolean> ensure(Activity activity, String permission){

        RxPermissions rxPermissions = RxPermissions.getInstance(activity);

        return  rxPermissions.ensure(permission);

    }

    public interface PermissionCallBack{
        void permissionGranted( Observable<Boolean> requestObservable);
        void permissionDenied(Observable<Boolean> requestObservable);
    }
}
