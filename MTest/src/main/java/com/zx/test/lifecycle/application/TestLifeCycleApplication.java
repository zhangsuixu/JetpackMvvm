package com.zx.test.lifecycle.application;

import android.app.Application;

import androidx.lifecycle.ProcessLifecycleOwner;

/**
 * ZhangSuiXu
 * 2021/9/5
 * <p>
 * 应用程序处于前台还是后台，Google并未提供官网解决方案
 * 直到Lifecycle出现,LifeCycle提供了名为ProcessLifecycleOwner的类，方便知道整个应用程序的生命周期情况
 */
public class TestLifeCycleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ProcessLifecycleOwner.get().getLifecycle()
                .addObserver(new ApplicationObserver());
    }
}
