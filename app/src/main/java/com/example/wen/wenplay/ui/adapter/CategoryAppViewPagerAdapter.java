package com.example.wen.wenplay.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wen.wenplay.ui.bean.FragmentInfo;
import com.example.wen.wenplay.ui.fragment.CategoryAppFragment;
import com.example.wen.wenplay.ui.fragment.CategoryFragment;
import com.example.wen.wenplay.ui.fragment.GamesFragment;
import com.example.wen.wenplay.ui.fragment.RecommendFragment;
import com.example.wen.wenplay.ui.fragment.TopListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2017/2/27.
 */

public class CategoryAppViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> titleList = new ArrayList<>(3);
    private int mCategoryId;

    public CategoryAppViewPagerAdapter(FragmentManager fm,int categoryId) {
        super(fm);
       this.mCategoryId = categoryId;
        titleList.add("精品");
        titleList.add("排行");
        titleList.add("新品");
    }




    @Override
    public Fragment getItem(int position) {

        return CategoryAppFragment.newInstance(mCategoryId,position);
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
