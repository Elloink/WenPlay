package com.example.wen.wenplay.data;

import com.example.wen.wenplay.bean.BaseBean;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.bean.Subject;
import com.example.wen.wenplay.bean.SubjectDetail;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.presenter.contract.SubjectContract;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/5/26.
 */

public class SubjectModel implements SubjectContract.ISubjectModel {
    private ApiService mApiService;

    public SubjectModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    @Override
    public Observable<BaseBean<PageBean<Subject>>> getSubject(int page) {
        return mApiService.subjects(page);
    }

    @Override
    public Observable<BaseBean<SubjectDetail>> getSubjectDetail(int id) {
        return mApiService.subjectDetail(id);
    }
}
