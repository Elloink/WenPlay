package com.example.wen.wenplay.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.bean.Subject;
import com.example.wen.wenplay.bean.SubjectDetail;
import com.example.wen.wenplay.common.rx.RxBus;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.di.component.DaggerSubjectComponent;
import com.example.wen.wenplay.di.module.SubjectModule;
import com.example.wen.wenplay.presenter.SubjectPresenterImpl;
import com.example.wen.wenplay.presenter.contract.SubjectContract;
import com.example.wen.wenplay.ui.activity.AppDetailActivity;
import com.example.wen.wenplay.ui.adapter.SubjectAdapter;
import com.example.wen.wenplay.ui.decoration.DividerItemDecoration;
import com.example.wen.wenplay.ui.decoration.SubjectDividerItemDecoration;


import butterknife.BindView;

/**
 * Created by wen on 2017/5/26.
 */

public class SubjectFragment extends BaseFragment<SubjectPresenterImpl> implements SubjectContract.SubjectView,BaseQuickAdapter.RequestLoadMoreListener{

    private SubjectAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private int page = 0;


    @Override
    public int setLayoutId() {
        return R.layout.template_recycler_view;
    }

    @Override
    public void setUpFragmentComponent(AppComponent appComponent) {
       DaggerSubjectComponent.builder().appComponent(appComponent).subjectModule(new SubjectModule(this)).build().injectSubjectFragment(this);
    }

    @Override
    public void init() {
        initRecyclerView();

        mPresenterImpl.getSubject(page);
    }

    protected void initRecyclerView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);


        mRecyclerView.setLayoutManager(gridLayoutManager);

        //DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        SubjectDividerItemDecoration itemDecoration = new SubjectDividerItemDecoration();

        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                Subject subject = mAdapter.getData().get(position);

                RxBus.getDefault().post(subject);
            }
        });

        mAdapter = new SubjectAdapter();

        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void showSubject(PageBean<Subject> subjects) {

        mAdapter.addData(subjects.getDatas());

        if (subjects.isHasMore()){
            page++;
        }

        mAdapter.setEnableLoadMore(subjects.isHasMore());


    }

    @Override
    public void showSubjectDetail(SubjectDetail subjectDetail) {

    }

    @Override
    public void onLoadMoreComplete() {
        mAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenterImpl.getSubject(page);
    }
}
