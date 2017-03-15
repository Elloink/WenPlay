package com.example.wen.wenplay.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wen.wenplay.ui.bean.FragmentInfo;
import com.example.wen.wenplay.ui.fragment.TopListFragment;
import com.example.wen.wenplay.ui.fragment.CategoryFragment;
import com.example.wen.wenplay.ui.fragment.GamesFragment;
import com.example.wen.wenplay.ui.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2017/2/27.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> mFragmentInfoList = new ArrayList<>(4);

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragment();
    }

    private void initFragment() {
        mFragmentInfoList.add(new FragmentInfo("推荐", RecommendFragment.class));
        mFragmentInfoList.add(new FragmentInfo("排行", TopListFragment.class));
        mFragmentInfoList.add(new FragmentInfo("游戏", GamesFragment.class));
        mFragmentInfoList.add(new FragmentInfo("分类", CategoryFragment.class));
    }


    @Override
    public Fragment getItem(int position) {

        try {
            return (Fragment) mFragmentInfoList.get(position).getFragment().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int getCount() {
        return mFragmentInfoList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentInfoList.get(position).getTitle();
    }
}
