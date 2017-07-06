package com.example.wen.wenplay.di.module;

import com.example.wen.wenplay.data.MainModel;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.presenter.MainPresenterImpl;
import com.example.wen.wenplay.presenter.contract.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/5/24.
 */
@Module
public class MainMudule {

    private MainContract.MainView mainView;


    public MainMudule(MainContract.MainView mainView) {
        this.mainView = mainView;
    }


    @Provides
    public MainContract.IMainModel providerMainModel(ApiService apiService){
        return new MainModel(apiService);
    }

    @Provides
    public MainContract.MainView providerMainView(){
        return mainView;
    }

    @Provides
    public MainPresenterImpl providerMainPresenterImpl(MainContract.IMainModel mainModel,MainContract.MainView mainView){
        return new MainPresenterImpl(mainModel,mainView);
    }


}
