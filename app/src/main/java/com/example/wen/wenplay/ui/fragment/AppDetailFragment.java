package com.example.wen.wenplay.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.imageloader.ImageLoader;
import com.example.wen.wenplay.common.util.DateUtils;
import com.example.wen.wenplay.di.component.AppComponent;

import com.example.wen.wenplay.di.component.DaggerAppDetialComponent;
import com.example.wen.wenplay.di.module.AppDetailModule;
import com.example.wen.wenplay.di.module.AppModelModule;
import com.example.wen.wenplay.presenter.AppDetailPresenterImpl;
import com.example.wen.wenplay.presenter.contract.AppInfoContract;
import com.example.wen.wenplay.ui.adapter.AppInfoAdapter;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wen on 2017/4/18.
 */

public class AppDetailFragment extends ProgressFragment<AppDetailPresenterImpl> implements AppInfoContract.AppDetailView {

    @BindView(R.id.view_gallery)
    LinearLayout mViewGallery;
    @BindView(R.id.expandable_text)
    TextView mExpandableText;
    @BindView(R.id.expand_collapse)
    ImageButton mExpandCollapse;
    @BindView(R.id.view_introduction)
    ExpandableTextView mViewIntroduction;
    @BindView(R.id.txt_update_time)
    TextView mTxtUpdateTime;
    @BindView(R.id.txt_version)
    TextView mTxtVersion;
    @BindView(R.id.txt_apk_size)
    TextView mTxtApkSize;
    @BindView(R.id.txt_publisher)
    TextView mTxtPublisher;
    @BindView(R.id.txt_publisher2)
    TextView mTxtPublisher2;
    @BindView(R.id.recycler_view_same_dev)
    RecyclerView mRecyclerViewSameDev;
    @BindView(R.id.recycler_view_relate)
    RecyclerView mRecyclerViewRelate;
    private int mAppId;
    private LayoutInflater mInflater;
    private AppInfoAdapter mAdapter;

    public static AppDetailFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt("app_id", id);
        AppDetailFragment fragment = new AppDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showResult(AppInfo appInfo) {
        //显示屏幕截图
        showScreenShot(appInfo.getScreenshot());

        //app 介绍
        mViewIntroduction.setText(appInfo.getIntroduction());

        mTxtUpdateTime.setText(DateUtils.formatDate(appInfo.getUpdateTime()));
        mTxtApkSize.setText((appInfo.getApkSize()/1024/1024)+"MB");
        mTxtVersion.setText(appInfo.getVersionName());
        mTxtPublisher.setText(appInfo.getPublisherName());
        mTxtPublisher2.setText(appInfo.getPublisherName());

        //相同开发者

        mAdapter = AppInfoAdapter.Builder().layout(R.layout.template_appinfo2)
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerViewSameDev.setLayoutManager(layoutManager);


        mAdapter.addData(appInfo.getSameDevAppInfoList());
        mRecyclerViewSameDev.setAdapter(mAdapter);

        /////////////////////////////////////////////

        //
        mAdapter = AppInfoAdapter.Builder().layout(R.layout.template_appinfo2)
                .build();

        mRecyclerViewRelate.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mAdapter.addData(appInfo.getRelateAppInfoList());
        mRecyclerViewRelate.setAdapter(mAdapter);
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_app_detail;
    }

    @Override
    public void setUpFragmentComponent(AppComponent appComponent) {
        DaggerAppDetialComponent.builder().appComponent(appComponent).appDetailModule(new AppDetailModule(this)).appModelModule(new AppModelModule())
                .build().inject(this);
    }

    @Override
    public void init() {
        Bundle arguments = getArguments();
        mAppId = arguments.getInt("app_id");
        mPresenterImpl.getAppdetail(mAppId);

        mInflater = LayoutInflater.from(getActivity());
    }

    private void showScreenShot(String screenShotString){
        String[] urls = screenShotString.split(",");

        for (String url:urls) {
            //将imageView 添加进LinearLayout mViewGallery中
            ImageView imageView = (ImageView) mInflater.inflate(R.layout.template_image_view,mViewGallery,false);

            ImageLoader.load(Constant.BASE_IMG_URL+url,imageView);

            mViewGallery.addView(imageView);

        }

    }

}
