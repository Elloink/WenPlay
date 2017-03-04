package com.example.wen.wenplay.presenter;

import com.example.wen.wenplay.ui.BaseView;

/**
 * Created by wen on 2017/2/28.
 */

public class BasePresenter<M,V extends BaseView> {

    protected M mModel;
    protected V mView;

    /**
     * @param model Presenter中使用的model
     * @param view Presenter中使用的view
     */
    public BasePresenter(M model, V view) {
        mModel = model;
        mView = view;
    }
}
