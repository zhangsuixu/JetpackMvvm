package com.zx.test.lifecycle.service;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * ZhangSuiXu
 * 2021/9/5
 */
public class LifeCycleServiceObserver implements LifecycleObserver {

    /** 当Activity执行onResume()方法时，该方法会被自动调用 */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void startGetLocation() {
        Log.d("LifeCycleService", "startGetLocation()");
    }

    /** 当Activity执行onPause()方法时，该方法会被自动调用 */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void stopGetLocation() {
        Log.d("LifeCycleService", "stopGetLocation()");
    }
}
