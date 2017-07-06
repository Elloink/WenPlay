package com.example.wen.wenplay.presenter.contract;

import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.Category;
import com.example.wen.wenplay.bean.LoginBean;
import com.example.wen.wenplay.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by wen on 2017/3/16.
 */

public interface CategoryContract {

    public interface ICategoryModel{

        Observable<BaseBean<List<Category>>> getAllCategory();

    }

    public interface CategoryView extends BaseView{

       void showData(List<Category> categories);
    }
}
