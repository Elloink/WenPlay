package com.example.wen.wenplay.di.module;


import com.example.wen.wenplay.data.CategoryModel;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.presenter.CategoryPresenterImpl;
import com.example.wen.wenplay.presenter.contract.CategoryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/2/28.
 */
@Module
public class CategoryModule {

    private CategoryContract.CategoryView mCategoryView;

    public CategoryModule(CategoryContract.CategoryView categoryView){
        this.mCategoryView = categoryView;
    }

    //需要的参数会从provides方法中找
    @Provides
   public CategoryPresenterImpl provideCategoryPresenterImpl(CategoryContract.ICategoryModel iCategoryModel, CategoryContract.CategoryView categoryView){
        return new CategoryPresenterImpl(iCategoryModel,categoryView);
    }


    @Provides
    public CategoryContract.CategoryView provideCategoryView(){
        return mCategoryView;
    }

    @Provides
    public CategoryContract.ICategoryModel provideICategoryModel(ApiService apiService){
        //由于此处参数ApiService 在HttpModule提供，因此需要在RecommendComponent中添加依赖
        return new CategoryModel(apiService);
    }
}
