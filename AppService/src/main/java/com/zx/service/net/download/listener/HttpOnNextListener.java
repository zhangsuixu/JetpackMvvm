package com.zx.service.net.download.listener;

import rx.Observable;

/**
 * 成功回调处理
 */
public abstract class HttpOnNextListener<T> {
    /**
     * 成功后回调方法
     */
    public abstract void onNext(T t);
    /**
     * 緩存回調結果
     */
    public void onCacheNext(String string){
    }
    /**
     * 成功后的ober返回，扩展链接式调用
     */
    public void onNext(Observable observable){
    }
    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     */
    public  void onError(Throwable e){ }
    /**
     * 取消回調
     */
    public void onCancel(){ }
}
