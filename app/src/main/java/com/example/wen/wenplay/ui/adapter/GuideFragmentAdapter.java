package com.example.wen.wenplay.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2017/3/4.
 */

public class GuideFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public GuideFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragmentList(List<Fragment> fragmentList){

        if (fragmentList == null){
            this.mFragmentList = new ArrayList<>();
        }
        this.mFragmentList = fragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
