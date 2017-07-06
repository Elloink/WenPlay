package com.example.wen.wenplay.di.component;

import com.example.wen.wenplay.di.FragmentScope;
import com.example.wen.wenplay.di.module.CategoryModule;
import com.example.wen.wenplay.ui.fragment.CategoryFragment;

import dagger.Component;

/**
 *
 * Created by wen on 2017/2/28.
 */
//(modules = RecommendModule.class)关联Module,由于依赖了AppComponent，而AppComponent的scope是Singleton，所以需要自定义一个比Singleton 级别低的scope
@FragmentScope
@Component(modules = CategoryModule.class,dependencies = AppComponent.class)
public interface CategoryComponent {

    void inject(CategoryFragment categoryFragment);

}
