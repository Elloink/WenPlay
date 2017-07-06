package com.example.wen.wenplay.data;

import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.SearchResult;
import com.example.wen.wenplay.data.db.SearchKeyWord;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.presenter.contract.SearchContract;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/5/31.
 */

public class SearchModel implements SearchContract.ISearchModel {

    private ApiService mApiService;

    public SearchModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    @Override
    public Observable<BaseBean<List<String>>> getSuggest(String keyWord) {
        return mApiService.searchSuggest(keyWord);
    }

    @Override
    public Observable<BaseBean<SearchResult>> searchResult(String keyWord) {
        return mApiService.search(keyWord);
    }

    @Override
    public Observable<List<SearchKeyWord>> getSearchKeyWord() {

        List<SearchKeyWord> searchKeyWordList = DataSupport.findAll(SearchKeyWord.class);
        return Observable.just(searchKeyWordList);
    }


}
