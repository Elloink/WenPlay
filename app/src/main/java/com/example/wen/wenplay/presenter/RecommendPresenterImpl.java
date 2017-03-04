package com.example.wen.wenplay.presenter;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.data.RecommendModel;
import com.example.wen.wenplay.presenter.contract.RecommendContract;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * RecommendPresenter 的具体实现
 * 负责与用户操作交互逻辑的控制
 * Created by wen on 2017/2/28.
 */

public class RecommendPresenterImpl extends BasePresenter<RecommendModel,RecommendContract.RecommendView> {

    //需要的参数dagger会到Module里面找
    @Inject
    public RecommendPresenterImpl(RecommendModel recommendModel,RecommendContract.RecommendView recommendView) {
       super(recommendModel,recommendView);

    }

    public void initDatas() {
        //获取到数据前提示Loading
        mView.showLoading();
        mModel.getApps(new Callback<PageBean<AppInfo>>() {
            @Override
            public void onResponse(Call<PageBean<AppInfo>> call, Response<PageBean<AppInfo>> response) {

                if (response != null){
                    //展示数据
                    PageBean<AppInfo> pageBean = response.body();
                    List<AppInfo> appInfoList = pageBean.getDatas();
                    mView.showResult(appInfoList);
                }else {
                    //提示没有数据
                    mView.showNoData();
                }
               //隐藏Loading
                mView.dismissLoading();
            }


            @Override
            public void onFailure(Call<PageBean<AppInfo>> call, Throwable t) {
                //失败，隐藏Loading并提示错误
                mView.dismissLoading();
                mView.showError(t.getMessage());
            }
        });
    }
}
