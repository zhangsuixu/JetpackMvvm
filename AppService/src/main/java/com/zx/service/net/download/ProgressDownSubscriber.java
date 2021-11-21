package com.zx.service.net.download;


import android.os.Handler;
import android.util.Log;

import com.zx.common.constant.DownState;
import com.zx.service.entity.po.DownloadInfoPO;
import com.zx.service.net.download.listener.DownloadProgressListener;
import com.zx.service.net.download.listener.HttpDownOnNextListener;

import java.lang.ref.SoftReference;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 断点下载处理类Subscriber
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class ProgressDownSubscriber<T> implements DownloadProgressListener, Observer<T> {
    //弱引用结果回调
    private SoftReference<HttpDownOnNextListener> mSubscriberOnNextListener;
    /*下载数据*/
    private DownloadInfoPO downInfo;
    private Handler handler;

    public ProgressDownSubscriber(DownloadInfoPO downInfo, Handler handler) {
        this.mSubscriberOnNextListener = new SoftReference<>(downInfo.getListener());
        this.downInfo = downInfo;
        this.handler = handler;
    }

    public void setDownInfo(DownloadInfoPO downInfo) {
        this.mSubscriberOnNextListener = new SoftReference<>(downInfo.getListener());
        this.downInfo = downInfo;
    }

    @Override
    public void update(long read, long count, boolean done) {
        if (downInfo.getCountLength() > count) {
            read = downInfo.getCountLength() - count + read;
        } else {
            downInfo.setCountLength(count);
        }
        downInfo.setReadLength(read);

        if (mSubscriberOnNextListener.get() == null || !downInfo.isUpdateProgress()) return;
        handler.post(new Runnable() {
            @Override
            public void run() {
                /*如果暂停或者停止状态延迟，不需要继续发送回调，影响显示*/
//                if (downInfo.getState() == DownState.PAUSE || downInfo.getState() == DownState.STOP)
//                    return;
                downInfo.setState(DownState.DOWN);

                mSubscriberOnNextListener.get().updateProgress(downInfo.getReadLength(), downInfo.getCountLength());
            }
        });
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onStart();
        }
        downInfo.setState(DownState.START);
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(@NonNull T t) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(t);
        }
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(@NonNull Throwable e) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onError(e);
        }
        HttpDownManager.getInstance().remove(downInfo);
        downInfo.setState(DownState.ERROR);
//        DbDownUtil.getInstance().update(downInfo);
    }


    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onComplete();
        }
        HttpDownManager.getInstance().remove(downInfo);
        downInfo.setState(DownState.FINISH);
//        DbDownUtil.getInstance().update(downInfo);
    }
}