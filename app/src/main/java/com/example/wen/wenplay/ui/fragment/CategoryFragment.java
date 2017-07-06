package com.example.wen.wenplay.ui.fragment;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.Category;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.di.component.AppComponent;

import com.example.wen.wenplay.di.component.DaggerCategoryComponent;
import com.example.wen.wenplay.di.module.CategoryModule;
import com.example.wen.wenplay.presenter.CategoryPresenterImpl;
import com.example.wen.wenplay.presenter.contract.CategoryContract;
import com.example.wen.wenplay.ui.activity.CategoryAppActivity;
import com.example.wen.wenplay.ui.adapter.CategoryAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wen on 2017/2/27.
 */

public class CategoryFragment extends ProgressFragment<CategoryPresenterImpl> implements CategoryContract.CategoryView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    public void showData(List<Category> categories) {
        if (mRecyclerView!=null)
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final CategoryAdapter adapter = new CategoryAdapter();
        adapter.addData(categories);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                Intent intent = new Intent(getActivity(), CategoryAppActivity.class);
                intent.putExtra(Constant.CATEGORY,adapter.getData().get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public int setLayoutId() {
        return R.layout.template_recycler_view;
    }

    @Override
    public void setUpFragmentComponent(AppComponent appComponent) {
        DaggerCategoryComponent.builder().appComponent(appComponent).categoryModule(new CategoryModule(this)).build().inject(this);
    }

    @Override
    public void init() {
        mPresenterImpl.getAllCategory();
    }

}
