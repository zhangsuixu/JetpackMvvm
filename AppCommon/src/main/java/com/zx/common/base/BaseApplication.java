package com.zx.common.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;

/**
 * ZhangSuiXu
 * 2021/11/22
 */
public class BaseApplication extends Application {

    private static Context context;
    private static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        if (null == context) {
            throw new RuntimeException("需要先设置Context");
        }
        return context;
    }

    public static Resources getResource() {
        return getContext().getResources();
    }


    public static Handler getMainHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }
}