package com.zx.service.entity.po;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.zx.common.constant.DownState;
import com.zx.service.net.ApiService;
import com.zx.service.net.download.listener.HttpDownOnNextListener;

/**
 * ZhangSuiXu
 */
@SuppressWarnings("rawtypes")
@Entity(tableName = "download_info")
public class DownloadInfoPO {

    @PrimaryKey
    private long id;

    /*存储位置*/
    private String savePath;
    /*文件总长度*/
    private long countLength;
    /*下载长度*/
    private long readLength;
    /*下载唯一的HttpService*/
    @Ignore //不需要存储数据库
    private ApiService service;
    /*回调监听*/
    @Ignore
    private HttpDownOnNextListener listener;
    /*超时设置*/
    private int connectionTime = 6;
    /*state状态数据库保存*/
    private int downState;
    /*url*/
    private String url;

//    private ObservableField<String> content = new ObservableField<>();

    @Ignore
    @Bindable
    private ObservableField<String> downloadTxt = new ObservableField<>();

    @Ignore
    public DownloadInfoPO(String url, HttpDownOnNextListener listener) {
        setUrl(url);
        setListener(listener);
    }

    @Ignore
    public DownloadInfoPO(String url) {
        setUrl(url);
    }

    public DownloadInfoPO() {
        readLength = 0;
        countLength = 0;
        downState = DownState.START.getState();
    }

    @Ignore
    public DownloadInfoPO(long id, String savePath, long countLength, long readLength,
                          int connectionTime, int downState, String url) {
        this.id = id;
        this.savePath = savePath;
        this.countLength = countLength;
        this.readLength = readLength;
        this.connectionTime = connectionTime;
        this.downState = downState;
        this.url = url;
    }

    public DownState getState() {
        switch (getDownState()) {
            case 0:
                return DownState.START;//未开始
            case 1:
                return DownState.DOWN;//下载中
            case 2:
                return DownState.PAUSE;//暂停
            case 3:
                return DownState.STOP;//停止
            case 4:
                return DownState.ERROR;//错误
            case 5:
            default:
                return DownState.FINISH;//完成
        }
    }

    public void setState(DownState state) {
        setDownState(state.getState());
    }


    public int getDownState() {
        return downState;
    }

    public void setDownState(int downState) {
        this.downState = downState;
    }

    public HttpDownOnNextListener getListener() {
        return listener;
    }

    public void setListener(HttpDownOnNextListener listener) {
        this.listener = listener;
    }

    public ApiService getService() {
        return service;
    }

    public void setService(ApiService service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }


    public long getCountLength() {
        return countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }


    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getConnectionTime() {
        return this.connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    @Ignore
    public ObservableField<String> getDownloadTxt() {
        return downloadTxt;
    }

    @Ignore
    public void setDownloadTxt(ObservableField<String> downloadTxt) {
        this.downloadTxt = downloadTxt;
    }
}
