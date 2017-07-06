package com.example.wen.wenplay.ui.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.imageloader.ImageLoader;
import com.example.wen.wenplay.common.util.DensityUtil;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.ui.fragment.AppDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppDetailActivity extends BaseActivity {


    @BindView(R.id.view_content)
    FrameLayout mViewContent;
    @BindView(R.id.view_temp)
    View mViewTemp;
    @BindView(R.id.img_icon)
    ImageView mImgIcon;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.txt_name)
    TextView mTxtName;
    @BindView(R.id.view_coordinator)
    CoordinatorLayout mViewCoordinator;

    private AppInfo mAppInfo;

    @Override
    public int setLayoutId() {
        return R.layout.activity_app_detail;
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
            supportActionBar.setTitle("");
        }

        //拿到传过来的appInfo
        mAppInfo = (AppInfo) getIntent().getSerializableExtra("app_info");

        mTxtName.setText(mAppInfo.getDisplayName());
        ImageLoader.load(Constant.BASE_IMG_URL+mAppInfo.getIcon(),mImgIcon);

        //拿到传入AppApplication中的View
        View appCacheView = mAppApplication.getAppCacheView();

        //拿到缓存Bitmap
        Bitmap viewImageCache = getViewImageCache(appCacheView);

        //拿到在屏幕的位置 注意这里的高度包括了状态栏的高度
        int[] outLocation = new int[2];
        appCacheView.getLocationOnScreen(outLocation);

        //拿到状态栏的高度
        int statusBarH = DensityUtil.getStatusBarH(this);

        //固定ImageView的位置
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(mViewTemp.getLayoutParams());
        //设置距离屏幕左边的位置和上边的位置和宽高  这里的高度则是距离状态栏下边的高度
        marginLayoutParams.leftMargin = outLocation[0];
        marginLayoutParams.topMargin = outLocation[1] - statusBarH;
        marginLayoutParams.width = appCacheView.getWidth();
        marginLayoutParams.height = appCacheView.getHeight();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(marginLayoutParams);
        mViewTemp.setLayoutParams(layoutParams);

        //将缓存Bitmap设置给mViewTemp
        if (viewImageCache != null) {
            mViewTemp.setBackground(new BitmapDrawable(viewImageCache));
        }

        //开启拉伸动画
        extendView();



    }

    /**
     * @param view 要获取缓存图像的View
     * @return view的缓存bitmap
     */
    private Bitmap getViewImageCache(View view) {

        view.setDrawingCacheEnabled(true);//可以缓存
        view.buildDrawingCache(); //绘制缓存

        Bitmap drawingCache = view.getDrawingCache(); //获取缓存bitmap

        if (drawingCache == null) {
            return null;
        }

        drawingCache = Bitmap.createBitmap(drawingCache, 0, 0, drawingCache.getWidth(), drawingCache.getHeight());

        view.destroyDrawingCache(); //destroy cache

        return drawingCache;
    }

    private void extendView() {

        int h = DensityUtil.getScreenH(this);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mViewTemp, "ScaleY", 1f, h);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                //动画开始后将背景设为白色
                mViewTemp.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mViewTemp.setVisibility(View.GONE);
                mViewCoordinator.setVisibility(View.VISIBLE);

                //动画结束后添加fragment
                addFragment();
            }

        });
        objectAnimator.setDuration(1000);
        //设置延时执行，让View有一个突出的效果
        objectAnimator.setStartDelay(700);
        objectAnimator.start();
    }

    private void addFragment() {
        AppDetailFragment appDetailFragment = AppDetailFragment.newInstance(mAppInfo.getId());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.view_content, appDetailFragment);
        // fragmentTransaction.commitAllowingStateLoss();
        fragmentTransaction.commit();
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
