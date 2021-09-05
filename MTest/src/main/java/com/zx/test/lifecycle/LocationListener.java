package com.zx.test.lifecycle;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * ZhangSuiXu
 * 2021/9/5
 */
public class LocationListener implements LifecycleObserver {

    private OnLocationChangeListener mOnLocationChangeListener;
    private Context mContext;

    public LocationListener(Activity context, OnLocationChangeListener listener) {
        mOnLocationChangeListener = listener;
        mContext = context;
        Log.d("LifeCycle", "initLocationManager()");
    }

    /** 当Activity执行onResume()方法时，该方法会被自动调用 */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void startGetLocation() {
        Log.d("LifeCycle", "startGetLocation()");
        if (null != mOnLocationChangeListener) {
            mOnLocationChangeListener.onChanged(1, 1);
        }
    }

    /** 当Activity执行onPause()方法时，该方法会被自动调用 */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void stopGetLocation() {
        Log.d("LifeCycle", "stopGEtLocation()");
    }

    /** 当Activity执行onDestroy()方法时，该方法会被自动调用 */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy(){
        mContext = null;
        mOnLocationChangeListener = null;
    }

    public interface OnLocationChangeListener {
        void onChanged(double latitude, double longitude);
    }
}
