package com.example.wen.wenplay.di.component;

import com.example.wen.wenplay.di.FragmentScope;
import com.example.wen.wenplay.di.module.RecommendModule;
import com.example.wen.wenplay.ui.fragment.RecommendFragment;

import dagger.Component;

/**
 *
 * Created by wen on 2017/2/28.
 */
//(modules = RecommendModule.class)关联Module,由于依赖了AppComponent，而AppComponent的scope是Singleton，所以需要自定义一个比Singleton 级别低的scope
@FragmentScope
@Component(modules = RecommendModule.class,dependencies = AppComponent.class)
public interface RecommendComponent {

    //与RecommendFragment关联
    void inject(RecommendFragment recommendFragment);

}
