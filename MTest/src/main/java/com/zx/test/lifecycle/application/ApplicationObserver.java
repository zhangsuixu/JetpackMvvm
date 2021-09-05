package com.zx.test.lifecycle.application;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * ZhangSuiXu
 * 2021/9/5
 */
public class ApplicationObserver implements LifecycleObserver {


    /** 应用程序整个生命周期中只会被调用一次 */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(){
        Log.d("ApplicationLifecycle","onCreate()");
    }

    /** 应用程序在前台出现时被调用 */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(){
        Log.d("ApplicationLifecycle","onStart()");
    }

    /** 应用程序在前台出现时被调用 */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        Log.d("ApplicationLifecycle","onResume()");
    }

    /** 应用程序退出到后台时被调用 */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        Log.d("ApplicationLifecycle","onPause()");
    }

    /** 应用程序退出到后台时被调用 */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(){
        Log.d("ApplicationLifecycle","onStop()");
    }

    /** 永远不会被调用，系统不会分发调用ON_DESTROY事件 */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        Log.d("ApplicationLifecycle","onDestroy()");
    }
}
