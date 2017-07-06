package com.example.wen.wenplay.di.component;

import com.example.wen.wenplay.di.FragmentScope;
import com.example.wen.wenplay.di.module.SubjectModule;
import com.example.wen.wenplay.ui.fragment.SubjectDetailFragment;
import com.example.wen.wenplay.ui.fragment.SubjectFragment;

import dagger.Component;

/**
 * Created by wen on 2017/5/26.
 */
@FragmentScope
@Component(modules = SubjectModule.class,dependencies = AppComponent.class)
public interface SubjectComponent {
    void injectSubjectFragment(SubjectFragment subjectFragment);
    void injectSubjectDetailFragment(SubjectDetailFragment subjectDetailFragment);
}
