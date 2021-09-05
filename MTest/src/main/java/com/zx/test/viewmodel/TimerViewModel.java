package com.zx.test.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ZhangSuiXu
 * 2021/9/5
 */
public class TimerViewModel extends ViewModel {

    private Timer mTimer;
    private int mCurrentSecond;
    private OnTimerChangeListener mOnTimerChangeListener;

    public void startTiming() {
        if (mTimer == null) {
            mCurrentSecond = 0;
            mTimer = new Timer();

            TimerTask timerTask = new TimerTask() {

                @Override
                public void run() {
                    mCurrentSecond++;

                    if (mOnTimerChangeListener != null) {
                        mOnTimerChangeListener.onTimerChanged(mCurrentSecond);
                    }
                }
            };

            mTimer.schedule(timerTask, 1000, 1000);
        }
    }

    public interface OnTimerChangeListener {
        void onTimerChanged(int second);
    }

    public void setOnTimerChangeListener(OnTimerChangeListener listener){
        mOnTimerChangeListener = listener;
    }

    /**
     * 当关联的Activity都被销毁时，会被系统调用
     * 注意 ：由于屏幕旋转而导致的Activity重建不会调用该方法
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        mTimer.cancel();
    }
}
