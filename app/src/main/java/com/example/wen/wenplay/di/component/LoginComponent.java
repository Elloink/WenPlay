package com.example.wen.wenplay.di.component;

import com.example.wen.wenplay.di.FragmentScope;
import com.example.wen.wenplay.di.module.AppInfoModule;
import com.example.wen.wenplay.di.module.LoginModule;
import com.example.wen.wenplay.ui.activity.LoginActivity;
import com.example.wen.wenplay.ui.fragment.GamesFragment;
import com.example.wen.wenplay.ui.fragment.TopListFragment;

import dagger.Component;

/**
 *
 * Created by wen on 2017/2/28.
 */
//(modules = RecommendModule.class)关联Module,由于依赖了AppComponent，而AppComponent的scope是Singleton，所以需要自定义一个比Singleton 级别低的scope
@FragmentScope
@Component(modules = LoginModule.class,dependencies = AppComponent.class)
public interface LoginComponent {

    void inject(LoginActivity loginActivity);

}
