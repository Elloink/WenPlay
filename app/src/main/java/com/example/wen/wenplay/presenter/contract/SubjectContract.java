package com.example.wen.wenplay.presenter.contract;

import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.bean.Subject;
import com.example.wen.wenplay.bean.SubjectDetail;
import com.example.wen.wenplay.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/5/26.
 */

public class SubjectContract {
    public interface ISubjectModel{
        Observable<BaseBean<PageBean<Subject>>> getSubject(int page);
        Observable<BaseBean<SubjectDetail>> getSubjectDetail(int id);
    }

    public interface SubjectView extends BaseView{
        void showSubject(PageBean<Subject> subjects);
        void showSubjectDetail(SubjectDetail subjectDetail);

        void onLoadMoreComplete();
    }

}
