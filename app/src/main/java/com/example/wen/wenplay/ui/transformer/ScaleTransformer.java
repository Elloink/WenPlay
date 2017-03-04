package com.example.wen.wenplay.ui.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 当前页滑动到左边，position从0渐变到-1；当前页滑动到右边，position从0渐变到1；
 * Created by wen on 2017/3/4.
 */

public class ScaleTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.70f;
    private static final float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(View page, float position) {
        if (position < - 1 || position >1){ //当前显示页面相邻的左和右两个页面
            page.setAlpha(MIN_ALPHA);
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1){ //[-1,1]
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            if (position < 0) {//当前页往左边滑动
                float scaleX = 1 + 0.3f * position; //position从0渐变到-1 {0，-0.1，-0.2，-0.3...}scaleX逐渐缩小
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
            } else { //当前页往右边滑动
                float scaleX = 1 - 0.3f * position; //position从0渐变到1{0，0.1，0.2，0.3...}scaleX逐渐缩小
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
            }
            page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        }
    }

}
