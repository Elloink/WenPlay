package com.example.wen.wenplay.presenter;

import com.example.wen.wenplay.bean.Category;
import com.example.wen.wenplay.bean.LoginBean;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.rx.RxHttpResponseCompat;
import com.example.wen.wenplay.common.rx.subscriber.ErrorHandleSubscriber;
import com.example.wen.wenplay.common.rx.subscriber.SweetProgressDialogSubscriber;
import com.example.wen.wenplay.common.util.ACache;
import com.example.wen.wenplay.common.util.VerificationUtils;
import com.example.wen.wenplay.presenter.contract.CategoryContract;
import com.example.wen.wenplay.presenter.contract.LoginContract;
import com.hwangjr.rxbus.RxBus;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by wen on 2017/3/16.
 */

public class CategoryPresenterImpl extends BasePresenter<CategoryContract.ICategoryModel,CategoryContract.CategoryView> {

    @Inject
    public CategoryPresenterImpl(CategoryContract.ICategoryModel model, CategoryContract.CategoryView view) {
        super(model, view);
    }

    public void getAllCategory(){
        mModel.getAllCategory().compose(RxHttpResponseCompat.<List<Category>>compatResult())
        .subscribe(new SweetProgressDialogSubscriber<List<Category>>(mContext,mView) {
            @Override
            public void onNext(List<Category> categories) {
                mView.showData(categories);
            }
        });
    }


}
