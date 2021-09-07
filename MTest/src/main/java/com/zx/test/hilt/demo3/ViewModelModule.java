package com.zx.test.hilt.demo3;

import androidx.lifecycle.SavedStateHandle;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

/**
 * ZhangSuiXu
 * 2021/9/6
 */
@Module
@InstallIn(ViewModelComponent.class)
public class ViewModelModule {

    @Provides
    public static TestHiltRepository provideRepo(SavedStateHandle handle) {
        return new TestHiltRepository();
    }
}