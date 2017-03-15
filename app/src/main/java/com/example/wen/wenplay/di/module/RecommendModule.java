package com.example.wen.wenplay.di.module;

import android.app.ProgressDialog;

import com.example.wen.wenplay.data.AppInfoModel;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.presenter.RecommendPresenterImpl;
import com.example.wen.wenplay.presenter.contract.AppInfoContract;
import com.example.wen.wenplay.ui.fragment.RecommendFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/2/28.
 */
@Module
public class RecommendModule {

    private AppInfoContract.RecommendView mRecommendView;

    //构造方法传入RecommendContract.RecommendView
    public RecommendModule(AppInfoContract.RecommendView recommendView){
        this.mRecommendView = recommendView;
    }

    //需要的参数会从provides方法中找
    @Provides
   public RecommendPresenterImpl provideRecommendPresenterImpl(AppInfoModel appInfoModel, AppInfoContract.RecommendView recommendView){
        return new RecommendPresenterImpl(appInfoModel,recommendView);
    }


    @Provides
    public AppInfoContract.RecommendView provideRecommendView(){
        return mRecommendView;
    }

    @Provides
    public AppInfoModel provideRecommendModel(ApiService apiService){
        //由于此处参数ApiService 在HttpModule提供，因此需要在RecommendComponent中添加依赖
        return new AppInfoModel(apiService);
    }

    @Provides
    public ProgressDialog provideProgressDialog(AppInfoContract.RecommendView recommendView){
        ProgressDialog progressDialog = new ProgressDialog(((RecommendFragment)recommendView).getActivity());
        progressDialog.setMessage("Loading...");
        return progressDialog;
    }
}
