package com.example.wen.wenplay.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.Subject;
import com.example.wen.wenplay.common.rx.RxBus;
import com.example.wen.wenplay.di.component.AppComponent;
import com.example.wen.wenplay.ui.fragment.SubjectDetailFragment;
import com.example.wen.wenplay.ui.fragment.SubjectFragment;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class SubjectActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private FragmentManager fragmentManager;
    private SubjectFragment subjectFragment;
    private SubjectDetailFragment subjectDetailFragment;
    private ActionBar supportActionBar;
    private final  int POSITION_SUBJECT_FRAGMENT = 0;
    private final  int POSITION_SUBJECT_DETAIL_FRAGMENT = 1;
    private int currentPosition = POSITION_SUBJECT_FRAGMENT;
    private SearchView searchView;


    @Override
    public int setLayoutId() {
        return R.layout.activity_subject;
    }

    @Override
    public void setUpActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void init() {

        initToolbar();

        fragmentManager = getSupportFragmentManager();

        showSubjectFragment();

        RxBus.getDefault().toObservable(Subject.class).subscribe(new Consumer<Subject>() {
            @Override
            public void accept(@NonNull Subject subject) throws Exception {
                showSubjectDetailFragment(subject);
            }
        });

    }

    private void showSubjectFragment() {

        supportActionBar.setTitle("主题");



        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        hideFragment(fragmentTransaction);

        if (subjectFragment == null ){
            subjectFragment = new SubjectFragment();
            fragmentTransaction.add(R.id.subject_container,subjectFragment);
        }else {
                fragmentTransaction.show(subjectFragment);
        }

        fragmentTransaction.commit();

        currentPosition = POSITION_SUBJECT_FRAGMENT;

    }

    private void showSubjectDetailFragment(Subject subject) {

        supportActionBar.setTitle(subject.getTitle());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (subjectDetailFragment != null ){
            fragmentTransaction.remove(subjectDetailFragment);
        }

        subjectDetailFragment = SubjectDetailFragment.newInstance(subject.getRelatedId());
        fragmentTransaction.add(R.id.subject_container,subjectDetailFragment);

        fragmentTransaction.commit();

        currentPosition = POSITION_SUBJECT_DETAIL_FRAGMENT;

    }

    private void  hideFragment(FragmentTransaction ft){

        if(subjectFragment != null){
            ft.hide(subjectFragment);
        }
        if(subjectDetailFragment != null){
            ft.hide(subjectDetailFragment);
        }


    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        supportActionBar = getSupportActionBar();
        if (supportActionBar != null ){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (currentPosition == POSITION_SUBJECT_FRAGMENT){
                    finish();
                }else {
                    showSubjectFragment();
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (currentPosition == POSITION_SUBJECT_FRAGMENT){
            finish();
        }else {
            showSubjectFragment();
        }
    }
}
