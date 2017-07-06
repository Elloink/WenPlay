package com.example.wen.wenplay.di.module;

import com.example.wen.wenplay.data.SearchModel;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.presenter.SearchPresenterImpl;
import com.example.wen.wenplay.presenter.contract.SearchContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/5/31.
 */
@Module
public class SearchModule {

    private SearchContract.SearchView mSearchView;

    public SearchModule(SearchContract.SearchView mSearchView) {
        this.mSearchView = mSearchView;
    }

    @Provides
    public SearchContract.ISearchModel provideISearchModel(ApiService apiService){
        return new SearchModel(apiService);
    }

    @Provides
    public SearchContract.SearchView provideSearchView(){
        return mSearchView;
    }

    @Provides
    public SearchPresenterImpl provideSearchPresenterImpl(SearchContract.ISearchModel searchModel, SearchContract.SearchView searchView){
        return new SearchPresenterImpl(searchModel,searchView);
    }
}
