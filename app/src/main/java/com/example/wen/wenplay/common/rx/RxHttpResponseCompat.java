package com.example.wen.wenplay.common.rx;

import android.util.Log;

import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.common.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * rx http response compat ,将每个请求公共的操作抽取出来并将结果进行转换，转换成需要的最终结果
 * Created by wen on 2017/3/7.
 */

public class RxHttpResponseCompat {

    public static <T> ObservableTransformer<BaseBean<T>,T> compatResult(){
        return new ObservableTransformer<BaseBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseBean<T>> baseBeanObservable) {
                return baseBeanObservable.flatMap(new Function<BaseBean<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(final BaseBean<T> tBaseBean) {
                        Log.d("RxHttpResponseCompat",tBaseBean.toString());
                        if (tBaseBean.success()){
                            //服务器请求成功
                            return Observable.create(new ObservableOnSubscribe<T>() {
                                @Override
                                public void subscribe(ObservableEmitter<T> e) throws Exception {
                                    try {
                                        //发送转换的数据回去
                                        e.onNext(tBaseBean.getData());
                                        e.onComplete();
                                    } catch (Exception e1) {
                                        e.onError(e1);
                                    }
                                }
                            });
                        }else {
                            //服务器出错，将服务器提供错误码和信息返回回去
                            return Observable.error(new ApiException(tBaseBean.getStatus(),tBaseBean.getMessage()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
