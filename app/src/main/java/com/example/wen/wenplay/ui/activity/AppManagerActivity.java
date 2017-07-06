package com.example.wen.wenplay.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wen.wenplay.R;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.ui.adapter.ViewPagerAdapter;
import com.example.wen.wenplay.ui.bean.FragmentInfo;
import com.example.wen.wenplay.ui.fragment.DoneFragment;
import com.example.wen.wenplay.ui.fragment.DownloadFragment;
import com.example.wen.wenplay.ui.fragment.InstalledFragment;
import com.example.wen.wenplay.ui.fragment.UpdateFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AppManagerActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private int position;

    @Override
    public int setLayoutId() {
        return R.layout.activity_app_manager;
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
            supportActionBar.setTitle("管理");
        }

        position = getIntent().getIntExtra(Constant.NEED_TO_UPDATE, 0);

        initTabLayout();
    }

    private void initTabLayout() {

        List<FragmentInfo> mFragmentInfoList = new ArrayList<>(4);
        mFragmentInfoList.add(new FragmentInfo("下载", DownloadFragment.class));
        mFragmentInfoList.add(new FragmentInfo("已完成", DoneFragment.class));
        mFragmentInfoList.add(new FragmentInfo("升级", UpdateFragment.class));
        mFragmentInfoList.add(new FragmentInfo("已安装", InstalledFragment.class));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),mFragmentInfoList);



        mViewPager.setAdapter(viewPagerAdapter);
        //关联tabLayout 和 ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

        if (position >0 ){
            mViewPager.setCurrentItem(2);
            if (mTabLayout.getTabAt(2) != null)
                mTabLayout.getTabAt(2).select();
        }else {
            mViewPager.setCurrentItem(0);
            if (mTabLayout.getTabAt(0) != null)
                mTabLayout.getTabAt(0).select();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
