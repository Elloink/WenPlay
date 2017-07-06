package com.example.wen.wenplay.presenter;

import android.widget.Toast;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.rx.RxHttpResponseCompat;
import com.example.wen.wenplay.common.rx.subscriber.ErrorHandleSubscriber;
import com.example.wen.wenplay.common.rx.subscriber.SweetProgressDialogSubscriber;
import com.example.wen.wenplay.common.util.ACache;
import com.example.wen.wenplay.data.AppInfoModel;
import com.example.wen.wenplay.presenter.contract.AppInfoContract;
import com.example.wen.wenplay.ui.fragment.CategoryAppFragment;


import java.util.List;

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


    //根据传过来的类型和page获取排行和游戏界面的应用数据
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


    //根据传过来的category id 和 flagType 获取对应的分类的对应精品、排行、新品应用数据
    public void getCategoryDate(int categoryId, int flagType,  int page){
       Observer observer = null;

        if (page == 0 ){
            observer = new SweetProgressDialogSubscriber<PageBean<AppInfo>>(mContext,mView) {
                @Override
                public void onNext(PageBean<AppInfo> pageBean) {
                    if (pageBean != null){
                        mView.showResult(pageBean);
                    }
                }
            };
        }else {
            observer = new ErrorHandleSubscriber<PageBean<AppInfo>>(mContext) {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(PageBean<AppInfo> pageBean) {
                    if (pageBean != null)
                    mView.showResult(pageBean);
                }

                @Override
                public void onComplete() {
                    mView.onLoadMoreComplete();
                }
            };
        }

        getCategoryObservable(categoryId,flagType,page).compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult()).subscribe(observer);

    }

    /**
     * @param categoryId 分类 ID
     * @param flagType 区分精品、排行、新品
     * @return 应用数据
     */
   private Observable<BaseBean<PageBean<AppInfo>>> getCategoryObservable(int categoryId,int flagType,int page){
            switch (flagType){
                case CategoryAppFragment.TYPE_BEST_LIST:
                    return mModel.getFeaturedAppByCategory(categoryId,page);

                case CategoryAppFragment.TYPE_TOP_LIST:
                    return mModel.getTopListAppByCategory(categoryId,page);

                case CategoryAppFragment.TYPE_NEW_LIST:
                    return mModel.getNewListAppByCategory(categoryId,page);

                default:
                    return Observable.empty();
            }
    }





}
