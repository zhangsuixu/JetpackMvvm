package com.zx.test.lifecycle.service;

import androidx.lifecycle.LifecycleService;

import com.zx.test.lifecycle.service.LifeCycleServiceObserver;

/**
 * ZhangSuiXu
 * 2021/9/5
 */
public class TestLifeCycleService extends LifecycleService {
    private LifeCycleServiceObserver mLifeCycleServiceObserver;

    public TestLifeCycleService(){
        mLifeCycleServiceObserver = new LifeCycleServiceObserver();

        getLifecycle().addObserver(mLifeCycleServiceObserver);
    }
}
