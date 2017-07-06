package com.example.wen.wenplay.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.common.apkparset.AndroidApk;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.presenter.AppManagerPresenterImpl;
import com.example.wen.wenplay.presenter.contract.AppManagerContract;
import com.example.wen.wenplay.ui.activity.BaseActivity;
import com.example.wen.wenplay.ui.decoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by wen on 2017/5/23.
 */

public abstract class BaseAppManagerFragment extends ProgressFragment<AppManagerPresenterImpl>  implements AppManagerContract.AppManagerView{

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAppManagerAdapter;

    @Override
    public void showRecord(List<DownloadRecord> downloadRecordList) {

    }


    @Override
    public void showDone(List<AndroidApk> androidApkList) {

    }

    @Override
    public void showInstalled(List<AndroidApk> androidApkList) {

    }

    @Override
    public void showUpdate(List<AppInfo> appInfoList) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.template_recycler_view;
    }


    @Override
    public void init() {
        initRecyclerView();
    }


    protected void initRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);

        mAppManagerAdapter = buildAdapter();


        mRecyclerView.setAdapter(mAppManagerAdapter);
    }

    abstract RecyclerView.Adapter buildAdapter();

}
