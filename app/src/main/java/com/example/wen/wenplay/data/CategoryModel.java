package com.example.wen.wenplay.data;

import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.Category;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.presenter.contract.CategoryContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/3/16.
 */

public class CategoryModel implements CategoryContract.ICategoryModel {
    private ApiService mApiService;

    public CategoryModel(ApiService apiService){
        this.mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<List<Category>>> getAllCategory() {
        return mApiService.category();
    }
}
