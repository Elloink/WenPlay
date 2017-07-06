package com.example.wen.wenplay.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.bean.Subject;
import com.example.wen.wenplay.bean.SubjectDetail;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.imageloader.ImageLoader;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.di.component.DaggerSubjectComponent;
import com.example.wen.wenplay.di.module.SubjectModule;
import com.example.wen.wenplay.presenter.SubjectPresenterImpl;
import com.example.wen.wenplay.presenter.contract.SubjectContract;
import com.example.wen.wenplay.ui.adapter.AppInfoAdapter;
import com.example.wen.wenplay.ui.decoration.DividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import zlc.season.rxdownload2.RxDownload;

/**
 * Created by wen on 2017/5/26.
 */

public class SubjectDetailFragment extends BaseFragment<SubjectPresenterImpl> implements SubjectContract.SubjectView {

    @BindView(R.id.imageview)
    ImageView imageview;
    @BindView(R.id.txt_desc)
    TextView txtDesc;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    AppInfoAdapter mAdapter;

    @Inject
    RxDownload mRxDownload;

    private int subjectId;

    public static SubjectDetailFragment newInstance(int subjectId) {

        Bundle args = new Bundle();
        args.putInt("subject_id", subjectId);
        SubjectDetailFragment fragment = new SubjectDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_subject_detail;
    }

    @Override
    public void setUpFragmentComponent(AppComponent appComponent) {
        DaggerSubjectComponent.builder().appComponent(appComponent).subjectModule(new SubjectModule(this)).build()
                .injectSubjectDetailFragment(this);
    }

    @Override
    public void init() {

        subjectId = getArguments().getInt("subject_id");

        initRecyclerView();

        mPresenterImpl.getSubjectDetail(subjectId);

    }


    protected void initRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);

        mAdapter = AppInfoAdapter.Builder().showBrief(false).showCategoryName(true)
                .rxDownload(mRxDownload).build();



        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showSubject(PageBean<Subject> subjects) {

    }

    @Override
    public void showSubjectDetail(SubjectDetail subjectDetail) {

        ImageLoader.load(Constant.BASE_IMG_URL + subjectDetail.getPhoneBigIcon(),imageview);

        txtDesc.setText(subjectDetail.getDescription());

        mAdapter.addData(subjectDetail.getListApp());
    }

    @Override
    public void onLoadMoreComplete() {

    }

}
