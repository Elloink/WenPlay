package com.example.wen.wenplay.presenter.contract;

import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.SearchResult;
import com.example.wen.wenplay.data.db.SearchKeyWord;
import com.example.wen.wenplay.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/5/31.
 */

public class SearchContract {
    public interface ISearchModel{
        Observable<BaseBean<List<String>>> getSuggest(String keyWord);
        Observable<BaseBean<SearchResult>> searchResult(String keyWord);
        Observable<List<SearchKeyWord>> getSearchKeyWord();
    }

    public interface SearchView extends BaseView{
        void showSuggest(List<String> suggests);
        void showSearchKeyWord(List<SearchKeyWord> searchKeyWords);
        void showSearchResult(SearchResult searchResult);
    }
}
