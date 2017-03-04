package com.example.wen.wenplay.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wen.wenplay.R;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.ui.adapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
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

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setUpActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void init() {

        initDrawerLayout();

        initTabLayout();

    }


    private void initDrawerLayout() {

        headerView = mNavigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "headerView 被点击", Toast.LENGTH_SHORT).show();

            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.menu_app_manage:
                        Toast.makeText(MainActivity.this, "应用管理 被点击", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.menu_message:
                        Toast.makeText(MainActivity.this, "消息中心 被点击", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.menu_setting:
                        Toast.makeText(MainActivity.this, "设置 被点击", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        //toolbar设置菜单
        mToolbar.inflateMenu(R.menu.menu_toolbar_menu);

        //drawerLayout 与 Toolbar 整合
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    private void initTabLayout() {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);
        //关联tabLayout 和 ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

    }

}
