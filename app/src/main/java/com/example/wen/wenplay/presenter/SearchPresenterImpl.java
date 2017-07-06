package com.example.wen.wenplay.presenter;


import com.example.wen.wenplay.bean.SearchResult;
import com.example.wen.wenplay.common.rx.RxHttpResponseCompat;
import com.example.wen.wenplay.common.rx.subscriber.SweetProgressDialogSubscriber;
import com.example.wen.wenplay.data.db.SearchKeyWord;
import com.example.wen.wenplay.presenter.contract.SearchContract;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Created by wen on 2017/5/31.
 */

public class SearchPresenterImpl extends BasePresenter<SearchContract.ISearchModel,SearchContract.SearchView> {

    @Inject
    public SearchPresenterImpl(SearchContract.ISearchModel model, SearchContract.SearchView view) {
        super(model, view);
    }

    /**
     * 获取关键字相关建议
     * @param keyWord
     */
    public void getSuggest(String keyWord){

        //将关键字保存到数据库
        saveSearchKeyWord(keyWord);

        mModel.getSuggest(keyWord).compose(RxHttpResponseCompat.<List<String>>compatResult())
                .subscribe(new SweetProgressDialogSubscriber<List<String>>(mContext,mView) {
                    @Override
                    public void onNext(List<String> strings) {
                        mView.showSuggest(strings);
                    }
                });
    }

    /**
     * 保存搜索关键字到数据库
     * @param keyWord
     */
    private void saveSearchKeyWord(String keyWord){
        SearchKeyWord searchKeyWord = new SearchKeyWord();
        searchKeyWord.setKeyWord(keyWord);
        searchKeyWord.save();
    }


    /**
     * 获取保存的搜索关键字
     */
    public void getSearchKeyWord(){

      mModel.getSearchKeyWord().subscribe(new SweetProgressDialogSubscriber<List<SearchKeyWord>>(mContext,mView) {
          @Override
          public void onNext(List<SearchKeyWord> searchKeyWords) {
              mView.showSearchKeyWord(searchKeyWords);
          }
      });

    }


    /**
     * 获取搜索结果
     * @param keyWord
     */
    public void getSearchResult(String keyWord){

        mModel.searchResult(keyWord).compose(RxHttpResponseCompat.<SearchResult>compatResult())
                .subscribe(new SweetProgressDialogSubscriber<SearchResult>(mContext,mView) {
                    @Override
                    public void onNext(SearchResult searchResult) {
                        mView.showSearchResult(searchResult);
                    }
                });
    }


}
