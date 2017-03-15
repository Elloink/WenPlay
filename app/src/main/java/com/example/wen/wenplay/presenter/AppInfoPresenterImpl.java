package com.example.wen.wenplay.presenter;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.common.rx.RxHttpResponseCompat;
import com.example.wen.wenplay.common.rx.subscriber.ErrorHandleSubscriber;
import com.example.wen.wenplay.common.rx.subscriber.SweetProgressDialogSubscriber;
import com.example.wen.wenplay.data.AppInfoModel;
import com.example.wen.wenplay.presenter.contract.AppInfoContract;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by wen on 2017/3/15.
 */

public class AppInfoPresenterImpl extends BasePresenter<AppInfoModel,AppInfoContract.AppInfoView> {

    public static final int TOP_LIST = 1;
    public static final int GAME = 2;


    /**
     * @param appInfoModel Presenter中使用的model
     * @param appInfoView  Presenter中使用的view
     */
    @Inject
    public AppInfoPresenterImpl(AppInfoModel appInfoModel, AppInfoContract.AppInfoView appInfoView) {
        super(appInfoModel, appInfoView);
    }


    public void requestData(int page,int type){

        Observer subscriber = null;
        if (page == 0){
            //只让第一页显示Loading
           subscriber = new SweetProgressDialogSubscriber<PageBean<AppInfo>>(mContext,mView) {
               @Override
               public void onNext(PageBean<AppInfo> pageBean) {
                   mView.showResult(pageBean);
               }
           };

        }else {
            subscriber = new ErrorHandleSubscriber<PageBean<AppInfo>>(mContext) {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(PageBean<AppInfo> pageBean) {
                    mView.showResult(pageBean);
                }

                @Override
                public void onComplete() {
                    mView.onLoadMoreComplete();
                }
            };
        }

        getObservable(page,type).compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult())
                .subscribe(subscriber);
    }

    private Observable<BaseBean<PageBean<AppInfo>>> getObservable(int page,int type){
        switch (type){
            case TOP_LIST:
                return mModel.topList(page);

            case GAME:
                return mModel.games(page);

            default:
                return Observable.empty();
        }

    }

}
