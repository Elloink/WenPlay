package com.example.wen.wenplay.di.module;

import android.app.ProgressDialog;

import com.example.wen.wenplay.data.RecommendModel;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.presenter.RecommendPresenterImpl;
import com.example.wen.wenplay.presenter.contract.RecommendContract;
import com.example.wen.wenplay.ui.fragment.RecommendFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/2/28.
 */
@Module
public class RecommendModule {

    private RecommendContract.RecommendView mRecommendView;

    //构造方法传入RecommendContract.RecommendView
    public RecommendModule(RecommendContract.RecommendView recommendView){
        this.mRecommendView = recommendView;
    }

    //需要的参数会从provides方法中找
    @Provides
   public RecommendPresenterImpl provideRecommendPresenterImpl(RecommendModel recommendModel,RecommendContract.RecommendView recommendView){
        return new RecommendPresenterImpl(recommendModel,recommendView);
    }


    @Provides
    public RecommendContract.RecommendView provideRecommendView(){
        return mRecommendView;
    }

    @Provides
    public RecommendModel provideRecommendModel(ApiService apiService){
        //由于此处参数ApiService 在HttpModule提供，因此需要在RecommendComponent中添加依赖
        return new RecommendModel(apiService);
    }

    @Provides
    public ProgressDialog provideProgressDialog(RecommendContract.RecommendView recommendView){
        return new ProgressDialog(((RecommendFragment)recommendView).getActivity());
    }
}
