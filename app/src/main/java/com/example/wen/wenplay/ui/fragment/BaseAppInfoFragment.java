package com.example.wen.wenplay.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.presenter.AppInfoPresenterImpl;
import com.example.wen.wenplay.presenter.contract.AppInfoContract;
import com.example.wen.wenplay.ui.activity.AppDetailActivity;
import com.example.wen.wenplay.ui.adapter.AppInfoAdapter;
import com.example.wen.wenplay.ui.decoration.DividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import zlc.season.rxdownload2.RxDownload;

/**
 * Created by wen on 2017/3/15.
 */

public abstract class BaseAppInfoFragment extends ProgressFragment<AppInfoPresenterImpl> implements AppInfoContract.AppInfoView,
        BaseQuickAdapter.RequestLoadMoreListener  {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    protected int page = 0;

    @Inject
    RxDownload rxDownload;

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
    protected void initRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                //点击将该View设置给AppApplication 全局获取（也可以使用RxBus进行传递）
                mAppApplication.setAppCacheView(view);
                Intent intent = new Intent(getActivity(), AppDetailActivity.class);
                intent.putExtra("app_info",mAppInfoAdapter.getData().get(position));
                startActivity(intent);
            }
        });

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
