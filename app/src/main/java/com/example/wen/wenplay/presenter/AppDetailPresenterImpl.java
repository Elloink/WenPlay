package com.example.wen.wenplay.presenter;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.common.rx.RxHttpResponseCompat;
import com.example.wen.wenplay.common.rx.subscriber.SweetProgressDialogSubscriber;
import com.example.wen.wenplay.data.AppInfoModel;
import com.example.wen.wenplay.presenter.contract.AppInfoContract;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/4/17.
 */

public class AppDetailPresenterImpl extends BasePresenter<AppInfoModel,AppInfoContract.AppDetailView> {

    @Inject
    public AppDetailPresenterImpl(AppInfoModel model, AppInfoContract.AppDetailView view) {
        super(model, view);
    }

    public void getAppdetail(int id){
        mModel.getAppDetail(id).compose(RxHttpResponseCompat.<AppInfo>compatResult())
                .subscribe(new SweetProgressDialogSubscriber<AppInfo>(mContext,mView) {
                    @Override
                    public void onNext(AppInfo appInfo) {
                        if (appInfo != null){
                            mView.showResult(appInfo);
                        }
                    }
                });
    }
}
