package com.example.wen.wenplay.di.module;

import com.example.wen.wenplay.data.SubjectModel;
import com.example.wen.wenplay.data.http.ApiService;
import com.example.wen.wenplay.presenter.SubjectPresenterImpl;
import com.example.wen.wenplay.presenter.contract.SubjectContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/5/26.
 */
@Module
public class SubjectModule {
    private SubjectContract.SubjectView mSubjectView;

    public SubjectModule(SubjectContract.SubjectView mSubjectView) {
        this.mSubjectView = mSubjectView;
    }

    @Provides
    public SubjectContract.ISubjectModel providerISubjectModel(ApiService apiService){
        return new SubjectModel(apiService);
    }


    @Provides
    public SubjectContract.SubjectView providerSubjectView(){
        return mSubjectView;
    }


    @Provides
    public SubjectPresenterImpl providerSubjectPresenterImpl(SubjectContract.ISubjectModel subjectModel, SubjectContract.SubjectView subjectView){
        return new SubjectPresenterImpl(subjectModel,subjectView);
    }

}
