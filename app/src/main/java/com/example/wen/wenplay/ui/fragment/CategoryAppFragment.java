package com.example.wen.wenplay.ui.fragment;

import android.os.Bundle;

import com.example.wen.wenplay.di.component.AppComponent;

import com.example.wen.wenplay.di.component.DaggerAppInfoComponent;
import com.example.wen.wenplay.di.module.AppInfoModule;
import com.example.wen.wenplay.di.module.AppModelModule;
import com.example.wen.wenplay.ui.adapter.AppInfoAdapter;

/**
 * Created by wen on 2017/4/16.
 */

public class CategoryAppFragment extends BaseAppInfoFragment{


    public static final int TYPE_BEST_LIST=0;
    public static final int TYPE_TOP_LIST=1;
    public static final int TYPE_NEW_LIST=2;

    private int mCategoryId;
    private int fragmentType;


    public static CategoryAppFragment newInstance(int category_id, int fragment_type) {

        Bundle args = new Bundle();
        args.putInt("category_id",category_id);
        args.putInt("fragment_type",fragment_type);
        CategoryAppFragment fragment = new CategoryAppFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init() {
        Bundle arguments = getArguments();
        if (arguments!=null){
            mCategoryId = arguments.getInt("category_id");
            fragmentType = arguments.getInt("fragment_type");
        }

        mPresenterImpl.getCategoryDate(mCategoryId,fragmentType,page);

        initRecyclerView();
    }


    @Override
    AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.Builder().showPosition(false).showBrief(true).showCategoryName(false).rxDownload(rxDownload).build();
    }

    @Override
    int type() {
        return 0;
    }

    @Override
    public void setUpFragmentComponent(AppComponent appComponent) {
        DaggerAppInfoComponent.builder().appComponent(appComponent).appModelModule(new AppModelModule()).appInfoModule(new AppInfoModule(this)).build().injectCategoryAppFragment(this);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenterImpl.getCategoryDate(mCategoryId,fragmentType,page);
    }
}
