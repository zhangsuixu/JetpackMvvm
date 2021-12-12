package com.download;

import android.os.Environment;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.zx.service.entity.po.DownloadInfoPO;
import com.zx.service.net.download.listener.HttpDownOnNextListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadViewModel extends ViewModel {

    private static final String URL1 = "https://ef08d3e545df199c2e2c316ab573b252.dlied1.cdntips.net/download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_2.apk?mkey=61b3df9e71633484&f=0000&cip=113.99.18.113&proto=https";
    private static final String URL2 = "http://222.78.41.124:49155/imtt.dd.qq.com/16891/apk/317489269A2DC57571F7EACCBB70D9C3.apk?mkey=61b3df089963bf41141c0cfc7c29682b&arrive_key=47511784529&fsname=tv.danmaku.bili_6.52.0_6520400.apk&csr=81e7&cip=113.103.10.1&proto=http";
    private static final String URL3 = "http://120.240.53.131:49155/imtt.dd.qq.com/16891/apk/1F8AB3A3BDD307B8C09A6DA093FA2ADB.apk?mkey=619adca2637089d24b170cfc8335d68d&arrive_key=149921548873&fsname=com.tencent.map_9.17.0_1416.apk&csr=81e7&cip=223.104.67.31&proto=http";
    private static final String URL4 = "http://222.78.41.124:49155/imtt.dd.qq.com/16891/apk/65104730DA407D616B11C7DD84210E4D.apk?mkey=61b3ddc39963bf41fc21b7fc7c29682b&arrive_key=35904181649&fsname=com.smwl.x7marketcp2_4.74.1.1_756.apk&csr=81e7&cip=113.103.10.1&proto=http";
    private static final String URL5 = "http://222.78.41.124:49155/imtt.dd.qq.com/16891/apk/96D02C917D6C8C17AF60451CEFA07F12.apk?mkey=61b3dd9a9963bf410d1c0cfc7c29682b&arrive_key=26906536104&fsname=cn.ninegame.gamemanager_7.7.1.3_70701003.apk&csr=81e7&cip=113.103.10.1&proto=http";
    private static final String URL6 = "http://120.240.53.132:49155/imtt.dd.qq.com/16891/apk/EB6E23DA98809B0E8E6A7C2040E7D516.apk?mkey=619adc76637089d2741b0cfc8435d68d&arrive_key=77545809168&fsname=dopool.player_8.6.6_503.apk&csr=81e7&cip=223.104.67.31&proto=http";

    private List<DownloadInfoPO> mDownloadInfoList;

    private ObservableField<String> mDownloadTxt;

    public ObservableField<String> getDownloadTxt() {
        if (mDownloadTxt == null) {
            mDownloadTxt = new ObservableField<>();
        }
        return mDownloadTxt;
    }

    public List<DownloadInfoPO> getDownloadInfoList() {
        if (mDownloadInfoList == null) {
            mDownloadInfoList = new ArrayList<>();
        }
        return mDownloadInfoList;
    }

    public List<DownloadInfoPO> getData() {
        if (mDownloadInfoList == null) {
            List<DownloadInfoPO> objects = new ArrayList<>();
            getDownloadInfoList();

            File outputFile = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS), "d1" + ".apk");
            Log.d("DownloadActivity", "file path :" + outputFile.getAbsolutePath());
            DownloadInfoPO apkApi = new DownloadInfoPO(URL1);
            apkApi.setId(1);
            apkApi.setSavePath(outputFile.getAbsolutePath());
            apkApi.setListener(new HttpDownOnNextListener() {
                @Override
                public void onNext(Object o) {

                }

                @Override
                public void onStart() {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void updateProgress(long readLength, long countLength) {
                    Log.d("DownloadActivitydddd", readLength + "--- updateProgress --- " + countLength + "  countLength");

                    mDownloadInfoList.get(0).getDownloadTxt().set(readLength + "/" + countLength);
                }
            });
            objects.add(apkApi);

            outputFile = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS), "d2" + ".apk");
            Log.d("DownloadActivity", "file path :" + outputFile.getAbsolutePath());
            apkApi = new DownloadInfoPO(URL2);
            apkApi.setId(2);
            apkApi.setSavePath(outputFile.getAbsolutePath());
            apkApi.setListener(new HttpDownOnNextListener() {
                @Override
                public void onNext(Object o) {

                }

                @Override
                public void onStart() {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void updateProgress(long readLength, long countLength) {
                    Log.d("DownloadActivitydddd", readLength + "--- updateProgress --- " + countLength + "  countLength");

                    mDownloadInfoList.get(1).getDownloadTxt().set(readLength + "/" + countLength);
                }
            });
            objects.add(apkApi);

            outputFile = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS), "b3" + ".apk");
            Log.d("DownloadActivity", "file path :" + outputFile.getAbsolutePath());
            apkApi = new DownloadInfoPO(URL3);
            apkApi.setId(3);
            apkApi.setSavePath(outputFile.getAbsolutePath());
            objects.add(apkApi);

            outputFile = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS), "b4" + ".apk");
            Log.d("DownloadActivity", "file path :" + outputFile.getAbsolutePath());
            apkApi = new DownloadInfoPO(URL4);
            apkApi.setId(4);
            apkApi.setSavePath(outputFile.getAbsolutePath());
            objects.add(apkApi);

            outputFile = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS), "b5" + ".apk");
            Log.d("DownloadActivity", "file path :" + outputFile.getAbsolutePath());
            apkApi = new DownloadInfoPO(URL5);
            apkApi.setId(5);
            apkApi.setSavePath(outputFile.getAbsolutePath());
            objects.add(apkApi);

            outputFile = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS), "b6" + ".apk");
            Log.d("DownloadActivity", "file path :" + outputFile.getAbsolutePath());
            apkApi = new DownloadInfoPO(URL6);
            apkApi.setId(6);
            apkApi.setSavePath(outputFile.getAbsolutePath());
            objects.add(apkApi);
            mDownloadInfoList.addAll(objects);
        }

        return mDownloadInfoList;
    }
}
