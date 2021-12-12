package com.zx.service.net.download;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.zx.common.constant.DownState;
import com.zx.common.utils.FileUtils;
import com.zx.service.db.base.DBUtils;
import com.zx.service.db.dao.DownloadInfoDao;
import com.zx.service.entity.po.DownloadInfoPO;
import com.zx.service.net.ApiService;
import com.zx.service.net.download.exception.HttpTimeException;
import com.zx.service.net.utils.AppUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("rawtypes")
public class HttpDownManager {

    private final Set<DownloadInfoPO> mDownInfoList;    /*记录下载数据*/
    private final HashMap<String, ProgressDownSubscriber> subMap;  /*回调sub队列*/
    private volatile static HttpDownManager INSTANCE;

    /*数据库类*/
    private DownloadInfoDao mDownloadInfoDao;
    /*下载进度回掉主线程*/
    private Handler handler;

    private HttpDownManager() {
        mDownInfoList = new HashSet<>();
        subMap = new HashMap<>();
        mDownloadInfoDao = DBUtils.getInstance().downloadInfoDao();
        handler = new Handler(Looper.getMainLooper());
    }

    public static HttpDownManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpDownManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDownManager();
                }
            }
        }
        return INSTANCE;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void startDown(final DownloadInfoPO info) {
        /*正在下载不处理*/
        if (info == null || subMap.get(info.getUrl()) != null) {
            subMap.get(info.getUrl()).setDownInfo(info);
            return;
        }
        ProgressDownSubscriber subscriber = new ProgressDownSubscriber(info, handler);   /*添加回调处理类*/
        subMap.put(info.getUrl(), subscriber);/*记录回调sub*/
        ApiService httpService;  /*获取service，多次请求公用一个sercie*/
        if (mDownInfoList.contains(info)) {
            httpService = info.getService();
        } else {
            DownloadInterceptor interceptor = new DownloadInterceptor(subscriber);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(1, TimeUnit.MINUTES);    //手动创建一个OkHttpClient并设置超时时间
//            builder.protocols(Collections.singletonList(Protocol.HTTP_1_1));
            builder.addInterceptor(interceptor);

            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .baseUrl(AppUtil.getBasUrl(info.getUrl()))
                    .build();
            httpService = retrofit.create(ApiService.class);
            info.setService(httpService);
            mDownInfoList.add(info);
        }

        /*得到rx对象-上一次下載的位置開始下載*/
        httpService.download("bytes=" + info.getReadLength() + "-", info.getUrl())
                /*指定线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*失败后的retry配置*/
//                .retryWhen(new RetryWhenNetworkException())
                /*读取下载写入文件*/
                .map(new Function<ResponseBody, Object>() {
                    @Override
                    public Object apply(ResponseBody responseBody) throws Throwable {
                        writeCaches(responseBody, new File(info.getSavePath()), info);
                        return info;
                    }
                })
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*数据回调*/
                .subscribe(subscriber);
    }

    public void stopDown(DownloadInfoPO info) {
        if (info == null) return;
        info.setState(DownState.STOP);
        info.getListener().onStop();
        if (subMap.containsKey(info.getUrl())) {
            ProgressDownSubscriber subscriber = subMap.get(info.getUrl());
            if (subscriber != null) {
                subscriber.unsubscribe();
            }
            subMap.remove(info.getUrl());
        }
        cacheData(info);
    }

    /**
     * 缓存当前下载进度及下载状态
     */
    private void cacheData(DownloadInfoPO info) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                mDownloadInfoDao.insert(info);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void pause(DownloadInfoPO info) {
        if (info == null) return;
        info.setState(DownState.PAUSE);
        info.getListener().onPuase();
        if (subMap.containsKey(info.getUrl())) {
            ProgressDownSubscriber subscriber = subMap.get(info.getUrl());
            if (subscriber != null) {
                subscriber.unsubscribe();
            }
            subMap.remove(info.getUrl());
        }
        cacheData(info);
    }

    public void stopAllDown() {
        for (DownloadInfoPO downInfo : mDownInfoList) {
            stopDown(downInfo);
        }
        subMap.clear();
        mDownInfoList.clear();
    }

    public void pauseAll() {
        for (DownloadInfoPO downInfo : mDownInfoList) {
            pause(downInfo);
        }
        subMap.clear();
        mDownInfoList.clear();
    }

    public Set<DownloadInfoPO> getDownInfoList() {
        return mDownInfoList;
    }

    /**
     * 移除下载数据
     */
    public void remove(DownloadInfoPO info) {
        subMap.remove(info.getUrl());
        mDownInfoList.remove(info);
    }

    public void writeCaches(ResponseBody responseBody, File file, DownloadInfoPO info) {
        RandomAccessFile randomAccessFile = null;
        FileChannel channelOut = null;
        InputStream inputStream = null;

        Long downloadLength = 0L;

        try {
            try {
                FileUtils.mkDir(file);

                long allLength = 0;
                if (info.getCountLength() == 0) {
                    allLength = responseBody.contentLength();
                } else {
                    allLength = info.getReadLength() + responseBody.contentLength();
                }

                inputStream = responseBody.byteStream();

                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                        info.getReadLength(), allLength - info.getReadLength());
                byte[] buffer = new byte[1024 * 8];
                int len = 0;
                while (true) {
                    //记录 鸿蒙系统下载过程会异常关闭流(https://yunpan.aliyun.com/downloads/apps/desktop/aDrive.exe?spm=aliyundrive.sign.0.0.13876c75mvMLGM&file=aDrive.exe)
                    len = inputStream.read(buffer);

                    if (len == -1) {
                        break;
                    }

                    mappedBuffer.put(buffer, 0, len);
                    downloadLength += len;
//                    Log.d("111111111111111111", "downloading progress : " + mDownloadLength
//                            + " -- allLength : " + allLength);
                }
            } catch (IOException e) {
                throw new HttpTimeException(e.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
        } catch (IOException e) {
            throw new HttpTimeException(e.getMessage());
        }
    }

}
