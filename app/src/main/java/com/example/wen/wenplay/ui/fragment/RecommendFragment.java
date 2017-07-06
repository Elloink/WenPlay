package com.example.wen.wenplay.ui.fragment;

import android.app.ProgressDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.IndexBean;
import com.example.wen.wenplay.di.component.AppComponent;
;
import com.example.wen.wenplay.di.component.DaggerRecommendComponent;
import com.example.wen.wenplay.di.module.RecommendModule;
import com.example.wen.wenplay.presenter.RecommendPresenterImpl;
import com.example.wen.wenplay.presenter.contract.AppInfoContract;
import com.example.wen.wenplay.ui.adapter.IndexMultipleAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import zlc.season.rxdownload2.RxDownload;


/**
 * 推荐fragment
 * Created by wen on 2017/2/27.
 */

public class RecommendFragment extends ProgressFragment<RecommendPresenterImpl> implements AppInfoContract.RecommendView{

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    //@Inject 告诉dagger ProgressDialog 需要dagger实例化，根据component关联，到相应的Module中的provides方法中得到实例化对象
    @Inject
     ProgressDialog mProgressDialog;

    @Inject
    RxDownload mRxDownload;

    @Override
    public int setLayoutId() {
        return R.layout.template_recycler_view;
    }

    @Override
    public void setUpFragmentComponent(AppComponent appComponent) {
        DaggerRecommendComponent.builder()
                .recommendModule(new RecommendModule(RecommendFragment.this))
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void init() {
        mPresenterImpl.initDatas();
    }

  /*  @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //在此注入可能导致空指针
        //注入
        DaggerRecommendComponent.builder().recommendModule(new RecommendModule(RecommendFragment.this)).build().inject(this);

    }*/


    private void initRecyclerView(IndexBean data) {

        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

     /*   //为RecyclerView设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST));*/

            //动画
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            IndexMultipleAdapter adapter = new IndexMultipleAdapter(getActivity(),mRxDownload,mAppApplication);

            adapter.setData(data);

            mRecyclerView.setAdapter(adapter);


        }
    }

    @Override
    public void showResult(IndexBean data) {
        initRecyclerView(data);

    }

    @Override
    public void onEmptyTextClick() {
        Toast.makeText(getActivity(),"服务器开小差了：",Toast.LENGTH_SHORT).show();
    }


}
