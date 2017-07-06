package com.example.wen.wenplay.di.component;

import com.example.wen.wenplay.di.FragmentScope;
import com.example.wen.wenplay.di.module.SearchModule;
import com.example.wen.wenplay.ui.activity.SearchActivity;

import dagger.Component;

/**
 * Created by wen on 2017/5/31.
 */
@FragmentScope
@Component(modules = SearchModule.class,dependencies = AppComponent.class)
public interface SearchComponent{
    void injectSearchActivity(SearchActivity searchActivity);
}
