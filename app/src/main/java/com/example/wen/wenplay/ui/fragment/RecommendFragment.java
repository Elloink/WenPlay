package com.example.wen.wenplay.ui.fragment;

import android.app.ProgressDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.IndexBean;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.di.component.DaggerRecommendComponent;
import com.example.wen.wenplay.di.module.RecommendModule;
import com.example.wen.wenplay.presenter.RecommendPresenterImpl;
import com.example.wen.wenplay.presenter.contract.RecommendContract;
import com.example.wen.wenplay.ui.adapter.IndexMultipleAdapter;
import com.example.wen.wenplay.ui.adapter.RecommendAdapter;
import com.example.wen.wenplay.ui.decoration.DividerItemDecoration;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * 推荐fragment
 * Created by wen on 2017/2/27.
 */

public class RecommendFragment extends ProgressFragment<RecommendPresenterImpl> implements RecommendContract.RecommendView{

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    //@Inject 告诉dagger ProgressDialog 需要dagger实例化，根据component关联，到相应的Module中的provides方法中得到实例化对象
    @Inject
     ProgressDialog mProgressDialog;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_recommend;
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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

     /*   //为RecyclerView设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST));*/

        //动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        IndexMultipleAdapter adapter = new IndexMultipleAdapter(getActivity());

        adapter.setData(data);

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showResult(IndexBean data) {
        initRecyclerView(data);

    }

    @Override
    public void onEmptyTextClick() {
        Toast.makeText(getActivity(),"服务器开小差了：",Toast.LENGTH_SHORT).show();
    }

    /*    @Override
    public void showError(String msg) {
        Log.d("RecommendFragment",msg);
        Toast.makeText(getActivity(),"服务器开小差了："+msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (mProgressDialog != null && !mProgressDialog.isShowing())
        mProgressDialog.show();
    }

    @Override
    public void dismissLoading() {
        if (mProgressDialog!=null && mProgressDialog.isShowing())
        mProgressDialog.dismiss();
    }*/
}
