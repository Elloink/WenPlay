package com.example.wen.wenplay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.Category;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.ui.adapter.CategoryAppViewPagerAdapter;

import butterknife.BindView;

public class CategoryAppActivity extends BaseActivity {


    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private Category mCategory;

    @Override
    public int setLayoutId() {
        return R.layout.activity_category_app;
    }

    @Override
    public void setUpActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void init() {

        setSupportActionBar(mToolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        getCategory();

        initTabLayout();

    }

    private void getCategory() {
        mCategory = (Category) getIntent().getSerializableExtra(Constant.CATEGORY);
    }

    private void initTabLayout() {
        CategoryAppViewPagerAdapter categoryAppViewPagerAdapter = new CategoryAppViewPagerAdapter(getSupportFragmentManager(),mCategory.getId());
        mViewPager.setAdapter(categoryAppViewPagerAdapter);
        //关联tabLayout 和 ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
