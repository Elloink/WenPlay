package com.example.wen.wenplay.di.component;

import com.example.wen.wenplay.di.FragmentScope;
import com.example.wen.wenplay.di.module.MainMudule;
import com.example.wen.wenplay.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by wen on 2017/5/24.
 */
@FragmentScope
@Component(modules = MainMudule.class,dependencies = AppComponent.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);
}
