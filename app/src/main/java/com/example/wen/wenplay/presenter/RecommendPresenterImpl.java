package com.example.wen.wenplay.presenter;


import android.Manifest;
import android.app.Activity;
import android.widget.Toast;

import com.example.wen.wenplay.bean.IndexBean;
import com.example.wen.wenplay.common.rx.RxHttpResponseCompat;
import com.example.wen.wenplay.common.rx.subscriber.SweetProgressDialogSubscriber;
import com.example.wen.wenplay.data.AppInfoModel;
import com.example.wen.wenplay.presenter.contract.AppInfoContract;
import com.tbruyelle.rxpermissions2.RxPermissions;


import javax.inject.Inject;

import io.reactivex.functions.Consumer;


/**
 * RecommendPresenter 的具体实现
 * 负责与用户操作交互逻辑的控制
 * Created by wen on 2017/2/28.
 */

public class RecommendPresenterImpl extends BasePresenter<AppInfoModel, AppInfoContract.RecommendView> {


    //需要的参数dagger会到Module里面找
    @Inject
    public RecommendPresenterImpl(AppInfoModel appInfoModel, AppInfoContract.RecommendView recommendView) {
        super(appInfoModel, recommendView);
    }

    public void initDatas() {
     /*
        RxPermissions rxPermissions = new RxPermissions((Activity) mContext);
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean){
                    getIndexBean();
                }else {
                    Toast.makeText(mContext,"你已拒绝授权",Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        getIndexBean();
    }


    public void getIndexBean(){
        mModel.index().compose(RxHttpResponseCompat.<IndexBean>compatResult())
                .subscribe(new SweetProgressDialogSubscriber<IndexBean>(mContext,mView) {
                    @Override
                    public void onNext(IndexBean indexBean) {
                        if (indexBean != null) {
                            //展示数据
                            mView.showResult(indexBean);
                        }
                    }
                });
    }

}
