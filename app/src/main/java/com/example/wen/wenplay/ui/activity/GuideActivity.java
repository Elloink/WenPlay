package com.example.wen.wenplay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wen.wenplay.R;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.util.ACache;
import com.example.wen.wenplay.ui.adapter.GuideFragmentAdapter;
import com.example.wen.wenplay.ui.fragment.GuideFragment;
import com.example.wen.wenplay.ui.transformer.ScaleTransformer;
import com.example.wen.wenplay.ui.widget.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.btn_enter)
    Button mBtnEnter;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;
    private GuideFragmentAdapter mGuideFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(GuideFragment.newInstance(R.drawable.guide_1,R.string.guide_text_1,R.color.color_bg_guide1));
        fragmentList.add(GuideFragment.newInstance(R.drawable.guide_2,R.string.guide_text_2,R.color.color_bg_guide2));
        fragmentList.add(GuideFragment.newInstance(R.drawable.guide_3,R.string.guide_text_3,R.color.color_bg_guide3));

        mGuideFragmentAdapter = new GuideFragmentAdapter(getSupportFragmentManager());
        mGuideFragmentAdapter.setFragmentList(fragmentList);

        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(mGuideFragmentAdapter.getCount());
        mViewPager.setPageTransformer(false,new ScaleTransformer());
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(mGuideFragmentAdapter);

        mIndicator.setViewPager(mViewPager);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {



    }


    @Override
    public void onPageSelected(int position) {

        mBtnEnter.setVisibility((position == mGuideFragmentAdapter.getCount() - 1) ? View.VISIBLE :View.GONE);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.btn_enter)
    public void onClick(){
        ACache.get(this).put(Constant.IS_SHOW_GUIDE,"0");
        startActivity(new Intent(this,MainActivity.class));
        this.finish();
    }

}
