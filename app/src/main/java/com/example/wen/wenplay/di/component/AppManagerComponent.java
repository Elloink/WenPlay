package com.example.wen.wenplay.di.component;

import com.example.wen.wenplay.di.FragmentScope;
import com.example.wen.wenplay.di.module.AppManagerModule;
import com.example.wen.wenplay.ui.fragment.DoneFragment;
import com.example.wen.wenplay.ui.fragment.DownloadFragment;
import com.example.wen.wenplay.ui.fragment.InstalledFragment;
import com.example.wen.wenplay.ui.fragment.UpdateFragment;

import dagger.Component;

/**
 * Created by wen on 2017/5/20.
 */
@FragmentScope
@Component(modules = AppManagerModule.class,dependencies = AppComponent.class)
public interface AppManagerComponent {
    void inject(DownloadFragment downloadFragment);
    void injectDoneFragment(DoneFragment doneFragment);
    void injectInstalledFragment(InstalledFragment installedFragment);
    void injectUpdateFragment(UpdateFragment updateFragment);
}
