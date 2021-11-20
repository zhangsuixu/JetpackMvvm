package com.zx.service.entity.po;

import androidx.annotation.Keep;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.zx.common.constant.DownState;
import com.zx.service.net.ApiService;
import com.zx.service.net.download.listener.HttpDownOnNextListener;

/**
 * ZhangSuiXu
 * 2021/9/7
 */
@Entity(tableName = "download_info")
public class DownloadInfoPO {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
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
    private int connectonTime = 6;
    /*state状态数据库保存*/
    private int stateInte;
    /*url*/
    private String url;
    /*是否需要实时更新下载进度,避免线程的多次切换*/
    private boolean updateProgress;


    public DownloadInfoPO(String url, HttpDownOnNextListener listener) {
        setUrl(url);
        setListener(listener);
    }

    public DownloadInfoPO(String url) {
        setUrl(url);
    }

    @Keep
    public DownloadInfoPO(long id, String savePath, long countLength, long readLength,
                    int connectonTime, int stateInte, String url) {
        this.id = id;
        this.savePath = savePath;
        this.countLength = countLength;
        this.readLength = readLength;
        this.connectonTime = connectonTime;
        this.stateInte = stateInte;
        this.url = url;
    }

    @Keep
    public DownloadInfoPO() {
        readLength = 0;
        countLength = 0;
        stateInte = DownState.START.getState();
    }

    public DownloadInfoPO(long id, String savePath, long countLength, long readLength,
                    int connectonTime, int stateInte, String url, boolean updateProgress) {
        this.id = id;
        this.savePath = savePath;
        this.countLength = countLength;
        this.readLength = readLength;
        this.connectonTime = connectonTime;
        this.stateInte = stateInte;
        this.url = url;
        this.updateProgress = updateProgress;
    }

    public DownState getState() {
        switch (getStateInte()) {
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

    public boolean isUpdateProgress() {
        return updateProgress;
    }

    public void setUpdateProgress(boolean updateProgress) {
        this.updateProgress = updateProgress;
    }

    public void setState(DownState state) {
        setStateInte(state.getState());
    }


    public int getStateInte() {
        return stateInte;
    }

    public void setStateInte(int stateInte) {
        this.stateInte = stateInte;
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

    public int getConnectonTime() {
        return this.connectonTime;
    }

    public void setConnectonTime(int connectonTime) {
        this.connectonTime = connectonTime;
    }

    public boolean getUpdateProgress() {
        return this.updateProgress;
    }

}
