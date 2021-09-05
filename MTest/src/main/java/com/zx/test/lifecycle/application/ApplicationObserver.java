package com.zx.test.lifecycle.application;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * ZhangSuiXu
 * 2021/9/5
 *
 * 注意 : 当应用从前退到后台时(用户按下Home键或者任务菜单键),会依次调用Lifecycle.Event.ON_PAUSE 和 Lifecycle.Event.ON_STOP。
 * 但具有一定的延时，因为系统需要保证因为需要为屏幕旋转，配置发生变化导致的Activity重建的情况预留一定的时间，因为这时程序并未退到后台
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
