package com.example.wen.wenplay.ui.activity;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.SearchResult;
import com.example.wen.wenplay.data.db.SearchKeyWord;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.di.component.DaggerSearchComponent;
import com.example.wen.wenplay.di.module.SearchModule;
import com.example.wen.wenplay.presenter.SearchPresenterImpl;
import com.example.wen.wenplay.presenter.contract.SearchContract;
import com.example.wen.wenplay.ui.adapter.AppInfoAdapter;
import com.example.wen.wenplay.ui.adapter.SuggestionAdapter;
import com.example.wen.wenplay.ui.decoration.DividerItemDecoration;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;

public class SearchActivity extends BaseActivity<SearchPresenterImpl> implements SearchContract.SearchView {


    /*@BindView(R.id.search_text_view)
    EditText searchTextView;
    @BindView(R.id.action_clear_btn)
    ImageView actionClearBtn;*/
    @BindView(R.id.img_clear_search_record)
    ImageView imgClearSearchRecord;
    @BindView(R.id.recycler_view_history)
    RecyclerView recyclerViewHistory;
    @BindView(R.id.recycler_view_suggestion)
    RecyclerView recyclerViewSuggestion;
    @BindView(R.id.recycler_view_result)
    RecyclerView recyclerViewResult;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;

    private SearchView.SearchAutoComplete searchAutoComplete;
    private SuggestionAdapter suggestionAdapter;
    private AppInfoAdapter appInfoAdapter;

    @Inject
    RxDownload rxDownload;
    private Disposable disposable;

    @Override
    public int setLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void setUpActivityComponent(AppComponent appComponent) {
        DaggerSearchComponent.builder().appComponent(appComponent).searchModule(new SearchModule(this)).build().injectSearchActivity(this);

    }

    @Override
    public void init() {

        setUpToolbar();

        setUpSearchView();

        suggestionAdapter = new SuggestionAdapter();
        appInfoAdapter = AppInfoAdapter.Builder().rxDownload(rxDownload).showBrief(false).showCategoryName(true).build();

        setUpRecyclerView(recyclerViewSuggestion);
        setUpRecyclerView(recyclerViewResult);
        recyclerViewSuggestion.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                searchAutoComplete.setText(suggestionAdapter.getData().get(position));
                search(suggestionAdapter.getData().get(position));
            }
        });

    }

    private void setUpRecyclerView(RecyclerView recyclerView) {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);

        recyclerView.addItemDecoration(itemDecoration);

    }

    private void setUpSearchView() {

        searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);

        //有文字显示删除按钮，否则隐藏
        searchView.onActionViewExpanded();
        //设置是否默认展开，true为不展开
        // searchView.setIconified(true);

        //获取搜索建议
        disposable = RxTextView.textChanges(searchAutoComplete).debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(@NonNull CharSequence charSequence) throws Exception {

                        if (charSequence.length() == 0) {
                            recyclerViewSuggestion.setVisibility(View.GONE);
                            llHistory.setVisibility(View.VISIBLE);
                            recyclerViewResult.setVisibility(View.GONE);
                        } else {
                            //获取搜索建议
                            presenterImpl.getSuggest(charSequence.toString());
                        }

                    }
                });

        RxTextView.editorActions(searchAutoComplete).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                search(searchAutoComplete.getText().toString());
            }
        });

    }

    private void search(String keyWord) {

      /*  if (disposable != null){
            disposable.dispose();
        }*/

        presenterImpl.getSearchResult(keyWord);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

    }


    @Override
    public void showSuggest(List<String> suggests) {


        suggestionAdapter.setNewData(suggests);

        recyclerViewSuggestion.setAdapter(suggestionAdapter);

        recyclerViewSuggestion.setVisibility(View.VISIBLE);
        llHistory.setVisibility(View.GONE);
        recyclerViewResult.setVisibility(View.GONE);
    }

    @Override
    public void showSearchKeyWord(List<SearchKeyWord> searchKeyWords) {
        recyclerViewSuggestion.setVisibility(View.GONE);
        llHistory.setVisibility(View.VISIBLE);
        recyclerViewResult.setVisibility(View.GONE);
    }

    @Override
    public void showSearchResult(SearchResult searchResult) {

        appInfoAdapter.setNewData(searchResult.getListApp());
        recyclerViewResult.setAdapter(appInfoAdapter);

        recyclerViewSuggestion.setVisibility(View.GONE);
        llHistory.setVisibility(View.GONE);
        recyclerViewResult.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

     /*   getMenuInflater().inflate(R.menu.menu_search,menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

      */


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //让菜单同时显示图标和文字
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {

                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }


        return super.onMenuOpened(featureId, menu);
    }

}
