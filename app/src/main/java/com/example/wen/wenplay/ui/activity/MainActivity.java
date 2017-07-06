package com.example.wen.wenplay.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.User;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.font.WenFont;
import com.example.wen.wenplay.common.imageloader.GlideCircleTransform;
import com.example.wen.wenplay.common.util.ACache;
import com.example.wen.wenplay.common.util.PermissionUtil;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.di.component.DaggerMainComponent;
import com.example.wen.wenplay.di.module.MainMudule;
import com.example.wen.wenplay.presenter.MainPresenterImpl;
import com.example.wen.wenplay.presenter.contract.MainContract;
import com.example.wen.wenplay.ui.adapter.ViewPagerAdapter;
import com.example.wen.wenplay.ui.bean.FragmentInfo;
import com.example.wen.wenplay.ui.fragment.CategoryFragment;
import com.example.wen.wenplay.ui.fragment.GamesFragment;
import com.example.wen.wenplay.ui.fragment.RecommendFragment;
import com.example.wen.wenplay.ui.fragment.TopListFragment;
import com.example.wen.wenplay.ui.widget.ToolbarActionProvider;
import com.hwangjr.rxbus.Bus;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

public class MainActivity extends BaseActivity<MainPresenterImpl> implements MainContract.MainView {
    @BindView(R.id.nv_left_menu)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private View headerView;
    private Bus rxBus;
    private ImageView mUserHeadView;
    private TextView mTextUserName;

    private static boolean isLogin;

    private ToolbarActionProvider mBadgeActionProvider;

    private int count;//需要更新的app个数

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setUpActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).mainMudule(new MainMudule(this)).build()
                .inject(this);
    }

    @Override
    public void init() {



        presenterImpl.requestPermission();

        rxBus = RxBus.get();
        //注册RxBus
        rxBus.register(this);

        presenterImpl.getUpdateAppInfo();

    /*    PermissionUtil.requestPermission(this, Manifest.permission.READ_PHONE_STATE, new PermissionUtil.PermissionCallBack() {
            @Override
            public void permissionGranted(Observable<Boolean> requestObservable) {

               isLogin = ACache.get(MainActivity.this).getAsObject(Constant.USER) == null ? false:true;

                initDrawerLayout();

                initTabLayout();

                initUser();
            }

            @Override
            public void permissionDenied(Observable<Boolean> requestObservable) {
                Toast.makeText(MainActivity.this,"您已拒绝授权 ！",Toast.LENGTH_SHORT).show();
            }
        });*/





    }

    @Subscribe
    public void receiveUser(User user){
        initUserHeadView(user);
    }

    private void initUserHeadView(User user){

          Glide.with(this).load(user.getLogo_url()).transform(new GlideCircleTransform(this)).into(mUserHeadView);

          mTextUserName.setText(user.getUsername());

        isLogin = true;
    }

    private void initDrawerLayout() {

        headerView = mNavigationView.getHeaderView(0);

        mUserHeadView = (ImageView) headerView.findViewById(R.id.img_account);
        mUserHeadView.setImageDrawable(new IconicsDrawable(this, WenFont.Icon.wen_account).colorRes(R.color.white));
        mTextUserName = (TextView) headerView.findViewById(R.id.txt_username);


        mNavigationView.getMenu().findItem(R.id.menu_app_update).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_refresh));
        mNavigationView.getMenu().findItem(R.id.menu_download_manager).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_download));
        mNavigationView.getMenu().findItem(R.id.menu_app_uninstall).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_delete));
        mNavigationView.getMenu().findItem(R.id.menu_setting).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_set));
        mNavigationView.getMenu().findItem(R.id.menu_logout).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_shutdown));

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch ( item.getItemId()){
                    case R.id.menu_logout:
                        Object objUser= ACache.get(MainActivity.this).getAsObject(Constant.USER);

                        if(objUser != null){
                            logout();
                        }
                        else{

                           Toast.makeText(MainActivity.this,"您还未登录",Toast.LENGTH_SHORT).show();

                        }
                        break;

                    case R.id.menu_download_manager:
                        Intent intent = new Intent(MainActivity.this,AppManagerActivity.class);
                        intent.putExtra(Constant.NEED_TO_UPDATE,count);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

        //toolbar设置菜单
        mToolbar.inflateMenu(R.menu.menu_toolbar_menu);
       /* mToolbar.getMenu().findItem(R.id.action_download_manager)
                .setIcon(new IconicsDrawable(this,WenFont.Icon.wen_download).colorRes(R.color.white).sizeDp(24));*/



        MenuItem item = mToolbar.getMenu().findItem(R.id.action_download_manager);
        mBadgeActionProvider = (ToolbarActionProvider) MenuItemCompat.getActionProvider(item);
        mBadgeActionProvider .setIcon(new IconicsDrawable(this,WenFont.Icon.wen_download).colorRes(R.color.white).sizeDp(20));
       // mBadgeActionProvider.setText("3");
        mBadgeActionProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AppManagerActivity.class);
                intent.putExtra(Constant.NEED_TO_UPDATE,count);
                startActivity(intent);
            }
        });


        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.action_search:
                        Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                        startActivity(intent);
                        break;
                }

                return false;
            }
        });

        //drawerLayout 与 Toolbar 整合
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    private void initUser(){

        Object objUser= ACache.get(this).getAsObject(Constant.USER);

        if(objUser == null){

          enterLogin();

        }
        else{

            User user = (User) objUser;
            initUserHeadView(user);
        }


    }

    private void enterLogin(){

            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isLogin) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                }
            });

    }

    /**
     * 退出登录
     */
    private void logout() {

        ACache.get(this).put(Constant.TOKEN,"");
        ACache.get(this).put(Constant.USER,"");


        mUserHeadView.setImageDrawable(new IconicsDrawable(this, WenFont.Icon.wen_account).colorRes(R.color.white));
        mTextUserName.setText("未登录");
        Toast.makeText(this,"您已退出登陆",Toast.LENGTH_SHORT).show();

        isLogin = false;
        enterLogin();

    }



    private void initTabLayout() {

        List<FragmentInfo> mFragmentInfoList = new ArrayList<>(4);
        mFragmentInfoList.add(new FragmentInfo("推荐", RecommendFragment.class));
        mFragmentInfoList.add(new FragmentInfo("排行", TopListFragment.class));
        mFragmentInfoList.add(new FragmentInfo("游戏", GamesFragment.class));
        mFragmentInfoList.add(new FragmentInfo("分类", CategoryFragment.class));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),mFragmentInfoList);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(viewPagerAdapter);
        //关联tabLayout 和 ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        rxBus.unregister(this);
    }

    @Override
    public void showNeedUpdateAppCount(int updateCount) {
        if (mBadgeActionProvider!= null) {
            if (updateCount > 0) {
                mBadgeActionProvider.setText(updateCount + "");
            } else {
                mBadgeActionProvider.hideBadge();
            }
        }

        count = updateCount;
    }

    @Override
    public void permissionGranted() {

        isLogin = ACache.get(MainActivity.this).getAsObject(Constant.USER) == null ? false:true;

        initDrawerLayout();

        initTabLayout();

        initUser();
    }

    @Override
    public void permissionDenied() {
        Toast.makeText(MainActivity.this,"您已拒绝授权 ！",Toast.LENGTH_SHORT).show();
    }
}
