package com.example.wen.wenplay.presenter;

import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.bean.Subject;
import com.example.wen.wenplay.bean.SubjectDetail;
import com.example.wen.wenplay.common.rx.RxHttpResponseCompat;
import com.example.wen.wenplay.common.rx.subscriber.ErrorHandleSubscriber;
import com.example.wen.wenplay.common.rx.subscriber.SweetProgressDialogSubscriber;
import com.example.wen.wenplay.presenter.contract.SubjectContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wen on 2017/5/26.
 */

public class SubjectPresenterImpl extends BasePresenter<SubjectContract.ISubjectModel,SubjectContract.SubjectView> {

    @Inject
    public SubjectPresenterImpl(SubjectContract.ISubjectModel model, SubjectContract.SubjectView view) {
        super(model, view);
    }

    public void getSubject(int page){
        if (page == 0 ){ //第一页显示loading view
            mModel.getSubject(page).compose(RxHttpResponseCompat.<PageBean<Subject>>compatResult()).subscribe(new SweetProgressDialogSubscriber<PageBean<Subject>>(mContext,mView) {
                @Override
                public void onNext(PageBean<Subject> subjectPageBean) {

                    mView.showSubject(subjectPageBean);

                }
            });
        }else {
            mModel.getSubject(page).compose(RxHttpResponseCompat.<PageBean<Subject>>compatResult()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ErrorHandleSubscriber<PageBean<Subject>>(mContext) {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(PageBean<Subject> subjectPageBean) {

                            mView.showSubject(subjectPageBean);
                        }

                        @Override
                        public void onComplete() {
                            mView.onLoadMoreComplete();
                        }
                    });
        }
    }

    public void getSubjectDetail(int id){
        mModel.getSubjectDetail(id).compose(RxHttpResponseCompat.<SubjectDetail>compatResult())
                .subscribe(new SweetProgressDialogSubscriber<SubjectDetail>(mContext,mView) {
            @Override
            public void onNext(SubjectDetail subjectDetail) {

                mView.showSubjectDetail(subjectDetail);
            }
        });
    }

}
