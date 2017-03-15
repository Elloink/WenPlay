package com.example.wen.wenplay.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.presenter.AppInfoPresenterImpl;
import com.example.wen.wenplay.presenter.contract.AppInfoContract;
import com.example.wen.wenplay.ui.adapter.AppInfoAdapter;
import com.example.wen.wenplay.ui.decoration.DividerItemDecoration;

import butterknife.BindView;

/**
 * Created by wen on 2017/3/15.
 */

public abstract class BaseAppInfoFragment extends ProgressFragment<AppInfoPresenterImpl> implements AppInfoContract.AppInfoView,
        BaseQuickAdapter.RequestLoadMoreListener  {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private int page = 0;

    private AppInfoAdapter mAppInfoAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.template_recycler_view;
    }


    @Override
    public void init() {
        mPresenterImpl.requestData(page,type());
        initRecyclerView();
    }
    private void initRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);

        mAppInfoAdapter = buildAdapter();

        mAppInfoAdapter.setOnLoadMoreListener(this,mRecyclerView);

        mRecyclerView.setAdapter(mAppInfoAdapter);
    }

    abstract AppInfoAdapter buildAdapter();
    abstract int type();
    @Override
    public void showResult(PageBean<AppInfo> data) {

        mAppInfoAdapter.addData(data.getDatas());

        if (data.isHasMore()){
            page++;
        }
        mAppInfoAdapter.setEnableLoadMore(data.isHasMore());
    }

    @Override
    public void onLoadMoreComplete() {
        mAppInfoAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenterImpl.requestData(page,type());
    }
}
