package com.example.wen.wenplay.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.annotation.AnimatorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.example.wen.wenplay.R;

/**
 * Created by wen on 2017/3/3.
 */

public class CircleIndicator extends LinearLayout {

    private static final int DEFAULT_INDICATOR_WIDTH = 5;  //默认宽
    private ViewPager mViewPager; //根据ViewPager页面数量动态创建小圆点个数
    private int mIndicatorMargin = -1; //Indicator外边距
    private int mIndicatorWidth = -1; //Indicator宽
    private int mIndicatorHeight = -1; //Indicator高
    private int mAnimatorResId = R.animator.scale_with_alpha; //属性动画资源ID
    private int mAnimatorReverseResId = 0; //还原属性动画资源ID
    private int mIndicatorBackgroundResId = R.drawable.red_radius; //选择状态Indicator背景资源ID
    private int mIndicatorUnselectedBackgroundResId = R.drawable.white_radius; //默认状态Indicator背景资源ID
    private Animator mAnimatorOut; //选中小圆点动画 对应mAnimatorResId
    private Animator mAnimatorIn; //未选中小圆点动画 对应mAnimatorReverseResId
    private Animator mImmediateAnimatorOut;//同mAnimatorOut duration为0
    private Animator mImmediateAnimatorIn;//同mAnimatorIn duration为0

    private int mLastPosition = -1; //记录ViewPager最后选择页面position


    //代码创建调用
    public CircleIndicator(Context context) {
        super(context);
        init(context,null);

    }

    //布局文件使用调用
    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    //布局文件使用调用，加入样式
    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    //布局文件使用调用，要求LOLLIPOP之后
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    /**
     * 初始化工作
     */
    private void init(Context context, AttributeSet attrs){
        handleTypeArray(context,attrs);
        checkIndicatorConfig(context);
    }

    /**
     * 拿到自定义属性
     */
    private void handleTypeArray(Context context, AttributeSet attrs) {
        if (attrs == null){
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);

        mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width,-1);

        mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_height,-1);

        mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_margin,-1);

        mAnimatorResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator, R.animator.scale_with_alpha);

        mAnimatorReverseResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator_reverse, 0);

        mIndicatorBackgroundResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable, R.drawable.red_radius);

        mIndicatorUnselectedBackgroundResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable_unselected, R.drawable.white_radius);

        //设置orientation
        int orientation = typedArray.getInt(R.styleable.CircleIndicator_ci_orientation, -1);
        setOrientation(orientation == VERTICAL ? VERTICAL : HORIZONTAL);

        //设置gravity
        int gravity = typedArray.getInt(R.styleable.CircleIndicator_ci_gravity, -1);
        setGravity(gravity >= 0 ? gravity : Gravity.CENTER);

        typedArray.recycle();

    }

    /**
     * 用于代码中动态配置Indicator属性（width，height，margin）
     */
    public void configureIndicator(int indicatorWidth,int indicatorHeight,int indicatorMargin){
        configureIndicator(indicatorWidth,indicatorHeight,indicatorMargin,R.animator.scale_with_alpha,
                0,R.drawable.red_radius,R.drawable.white_radius);
    }
    /**
     * 用于代码中动态配置Indicator属性（width，height，margin,animator,animatorReverse,indicatorBackground,indicatorUnselectedBackground）
     */
    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin,
                                    @AnimatorRes int animatorResId, @AnimatorRes int animatorReverseResId,
                                    @DrawableRes int indicatorBackgroundResId,@DrawableRes int indicatorUnselectedBackgroundResId){
        mIndicatorWidth = indicatorWidth;
        mIndicatorHeight = indicatorHeight;
        mIndicatorMargin = indicatorMargin;

        mAnimatorResId = animatorResId;
        mAnimatorReverseResId = animatorReverseResId;
        mIndicatorBackgroundResId = indicatorBackgroundResId;
        mIndicatorUnselectedBackgroundResId = indicatorUnselectedBackgroundResId;

        checkIndicatorConfig(getContext());

    }

    /**
     *检查用户配置是否合理
     */
    private void checkIndicatorConfig(Context context) {

        mIndicatorWidth = (mIndicatorWidth < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorWidth;

        mIndicatorHeight = (mIndicatorHeight < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorHeight;

        mIndicatorMargin = (mIndicatorMargin < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorMargin;

        mAnimatorResId = (mAnimatorResId == 0) ? R.animator.scale_with_alpha : mAnimatorResId;

        //出动画
        mAnimatorOut = createAnimatorOut(context);
        mImmediateAnimatorOut = createAnimatorOut(context);
        mImmediateAnimatorOut.setDuration(0);

        //入动画
        mAnimatorIn = createAnimatorIn(context);
        mImmediateAnimatorIn = createAnimatorIn(context);
        mImmediateAnimatorIn.setDuration(0);


        mIndicatorBackgroundResId = (mIndicatorBackgroundResId == 0) ? R.drawable.white_radius : mIndicatorBackgroundResId;

        mIndicatorUnselectedBackgroundResId = (mIndicatorUnselectedBackgroundResId == 0) ? mIndicatorBackgroundResId
                : mIndicatorUnselectedBackgroundResId;
    }

    private Animator createAnimatorIn(Context context) {

        Animator animatorIn;
        if (mAnimatorReverseResId == 0){
            //未设置相对属性动画，使用默认相对动画，即执行相反效果
            animatorIn = AnimatorInflater.loadAnimator(context, mAnimatorResId);
            //设置自定义插值器
            animatorIn.setInterpolator(new ReverseInterpolator());
        }else {
            animatorIn = AnimatorInflater.loadAnimator(context,mAnimatorReverseResId);
        }

        return animatorIn;
    }

    private Animator createAnimatorOut(Context context) {
        return AnimatorInflater.loadAnimator(context,mAnimatorResId);
    }

    /**
     * 设置ViewPager后根据ViewPager页面个数创建小圆点（indicator）
     */
    public void setViewPager(ViewPager viewPager){

        mViewPager = viewPager;
        if (mViewPager != null && mViewPager.getAdapter() != null){

            //设置viewPager时清空mLastPosition记录状态
            mLastPosition = -1;

            //创建indicator
            createIndicators();
            mViewPager.removeOnPageChangeListener(mInternalPageChangeListener);
            mViewPager.addOnPageChangeListener(mInternalPageChangeListener);
            mInternalPageChangeListener.onPageSelected(mViewPager.getCurrentItem());

        }

    }


    private final ViewPager.OnPageChangeListener mInternalPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override public void onPageSelected(int position) {

            if (mViewPager.getAdapter() == null || mViewPager.getAdapter().getCount() <= 0) {
                return;
            }

            if (mAnimatorIn.isRunning()) {
                mAnimatorIn.end();
                mAnimatorIn.cancel();
            }

            if (mAnimatorOut.isRunning()) {
                mAnimatorOut.end();
                mAnimatorOut.cancel();
            }

            View currentIndicator;
            if (mLastPosition >= 0 && (currentIndicator = getChildAt(mLastPosition)) != null) {
                currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
                mAnimatorIn.setTarget(currentIndicator);
                mAnimatorIn.start();
            }

            View selectedIndicator = getChildAt(position);
            if (selectedIndicator != null) {
                selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId);
                mAnimatorOut.setTarget(selectedIndicator);
                mAnimatorOut.start();
            }
            mLastPosition = position;
        }

        @Override public void onPageScrollStateChanged(int state) {
        }
    };


    public DataSetObserver getDataSetObserver() {
        return mInternalDataSetObserver;
    }

    //viewPager页面数量观察者
    private DataSetObserver mInternalDataSetObserver = new DataSetObserver() {
        @Override public void onChanged() {
            super.onChanged();
            if (mViewPager == null) {
                return;
            }

            int newCount = mViewPager.getAdapter().getCount();
            int currentCount = getChildCount();

            if (newCount == currentCount) {  // No change
                return;
            } else if (mLastPosition < newCount) {
                mLastPosition = mViewPager.getCurrentItem();
            } else {
                mLastPosition = -1;
            }

            createIndicators();
        }
    };

    /**
     * @deprecated User ViewPager addOnPageChangeListener
     */
    @Deprecated public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        if (mViewPager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        }
        mViewPager.removeOnPageChangeListener(onPageChangeListener);
        mViewPager.addOnPageChangeListener(onPageChangeListener);
    }



    /**
     * 根据ViewPager页面个数创建小圆点（indicator）
     */
    private void createIndicators() {
        removeAllViews();

        int count = mViewPager.getAdapter().getCount();
        if (count == 0){
            return;
        }

        int currentItem = mViewPager.getCurrentItem();

        for (int i = 0; i < count; i++) {
            if (currentItem == i) {
                addIndicator(mIndicatorBackgroundResId, mImmediateAnimatorOut);
            } else {
                addIndicator(mIndicatorUnselectedBackgroundResId, mImmediateAnimatorIn);
            }
        }

    }

    private void addIndicator(@DrawableRes int backgroundDrawableId, Animator animator) {

        if (animator.isRunning()) {
            animator.end();
            animator.cancel();
        }

        View Indicator = new View(getContext());
        Indicator.setBackgroundResource(backgroundDrawableId);
        addView(Indicator, mIndicatorWidth, mIndicatorHeight);
        LayoutParams lp = (LayoutParams) Indicator.getLayoutParams();
        lp.leftMargin = mIndicatorMargin;
        lp.rightMargin = mIndicatorMargin;
        Indicator.setLayoutParams(lp);

        animator.setTarget(Indicator);
        animator.start();
    }

    /**
     * 自定义相对插值器
     */
    private class ReverseInterpolator implements Interpolator{

        @Override
        public float getInterpolation(float input) {
            //input
            //相反效果
            return Math.abs(1.0f - input);
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
