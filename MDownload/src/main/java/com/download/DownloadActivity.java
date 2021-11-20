package com.download;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zx.service.entity.po.DownloadInfoPO;
import com.zx.service.net.download.HttpDownManager;

import java.io.File;

public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "aDrive" + ".exe");
        Log.d("11111111111111", outputFile.getAbsolutePath());
//        DownloadInfoPO apkApi = new DownloadInfoPO("http://music.163.com/song/media/outer/url?id=1895800471.mp3");
        DownloadInfoPO apkApi = new DownloadInfoPO("https://yunpan.aliyun.com/downloads/apps/desktop/aDrive.exe?spm=aliyundrive.sign.0.0.13876c75mvMLGM&file=aDrive.exe");

        apkApi.setId(1);
        apkApi.setUpdateProgress(true);
        apkApi.setSavePath(outputFile.getAbsolutePath());
        HttpDownManager.getInstance().startDown(apkApi);
    }
}
