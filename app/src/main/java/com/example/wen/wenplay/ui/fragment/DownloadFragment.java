package com.example.wen.wenplay.ui.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wen.wenplay.R;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.di.component.DaggerAppManagerComponent;
import com.example.wen.wenplay.di.module.AppManagerModule;
import com.example.wen.wenplay.presenter.AppManagerPresenterImpl;
import com.example.wen.wenplay.presenter.contract.AppManagerContract;
import com.example.wen.wenplay.ui.adapter.DownloadFragmentAdapter;
import com.example.wen.wenplay.ui.decoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by wen on 2017/5/19.
 */

public class DownloadFragment extends BaseAppManagerFragment {

    private DownloadFragmentAdapter mDownloadFragmentAdapter;
    @Override
    BaseQuickAdapter buildAdapter() {
        mDownloadFragmentAdapter = new DownloadFragmentAdapter(mPresenterImpl.getRxDownload());
        return mDownloadFragmentAdapter;
    }

    @Override
    public void setUpFragmentComponent(AppComponent appComponent) {
        DaggerAppManagerComponent.builder().appComponent(appComponent).appManagerModule(new AppManagerModule(this)).build().inject(this);
    }

    @Override
    public void init() {
        super.init();

        mPresenterImpl.getDownloadRecord();

    }

    /*  @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;



    @Override
    public int setLayoutId() {
        return R.layout.template_recycler_view;
    }

    @Override
    public void setUpFragmentComponent(AppComponent appComponent) {
        DaggerAppManagerComponent.builder().appComponent(appComponent).appManagerModule(new AppManagerModule(this)).build().inject(this);
    }

    @Override
    public void init() {

        initRecyclerView();

        mPresenterImpl.getDownloadRecord();
    }

    private void initRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);

        mDownloadFragmentAdapter = new DownloadFragmentAdapter(mPresenterImpl.getRxDownload());

        mRecyclerView.setAdapter(mDownloadFragmentAdapter);
    }


  */

    @Override
    public void showRecord(List<DownloadRecord> downloadRecordList) {

        mDownloadFragmentAdapter.addData(downloadRecordList);

    }

}
