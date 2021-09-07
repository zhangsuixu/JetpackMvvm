package com.zx.test.hilt.demo3;

import androidx.lifecycle.SavedStateHandle;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

/**
 * ZhangSuiXu
 * 2021/9/6
 *
 * Module类
 * @Provides 主要用于提供对象，避免在生成的地方进行对象的构建。
 * @InstallIn 主要用于限制对象的生命周期，解决在生命周期结束时手动释放的问题。
 * 下方例如 ：
 *   将TestHiltRepository对象提供给TestHiltViewModel，同时该对象和ViewModel的生命周期绑定。
 *
 */
@Module
@InstallIn(ViewModelComponent.class)
public class ViewModelModule {

    @Provides
    public static TestHiltRepository provideRepo() {
        return new TestHiltRepository();
    }
}