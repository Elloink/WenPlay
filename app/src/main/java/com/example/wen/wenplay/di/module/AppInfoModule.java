package com.example.wen.wenplay.di.module;

import com.example.wen.wenplay.data.AppInfoModel;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.presenter.AppInfoPresenterImpl;
import com.example.wen.wenplay.presenter.contract.AppInfoContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/2/28.
 */
@Module(includes = {AppModelModule.class})
public class AppInfoModule {

    private AppInfoContract.AppInfoView mAppInfoView;

    //构造方法传入
    public AppInfoModule(AppInfoContract.AppInfoView appInfoView){
        this.mAppInfoView = appInfoView;
    }

    //需要的参数会从provides方法中找
    @Provides
   public AppInfoPresenterImpl provideTopListPresenterImpl(AppInfoModel appInfoModel, AppInfoContract.AppInfoView appInfoView){
        return new AppInfoPresenterImpl(appInfoModel, appInfoView);
    }


    @Provides
    public AppInfoContract.AppInfoView provideTopListView(){
        return mAppInfoView;
    }

  /*  @Provides
    public AppInfoModel provideRecommendModel(ApiService apiService){
        //由于此处参数ApiService 在HttpModule提供，因此需要在RecommendComponent中添加依赖
        return new AppInfoModel(apiService);
    }*/

}
