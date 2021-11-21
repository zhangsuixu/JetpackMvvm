package com.download;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.zx.service.entity.po.DownloadInfoPO;
import com.zx.service.net.download.HttpDownManager;
import com.zx.service.net.download.listener.HttpDownOnNextListener;

import java.io.File;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.READ_EXTERNAL_STORAGE)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                // 申请多个权限
                //.permission(Permission.Group.CALENDAR)
                // 申请安装包权限
                //.permission(Permission.REQUEST_INSTALL_PACKAGES)
                // 申请悬浮窗权限
                //.permission(Permission.SYSTEM_ALERT_WINDOW)
                // 申请通知栏权限
                //.permission(Permission.NOTIFICATION_SERVICE)
                // 申请系统设置权限
                //.permission(Permission.WRITE_SETTINGS)
                // 设置权限请求拦截器
                //.interceptor(new PermissionInterceptor())
                // 设置不触发错误检测机制
                //.unchecked()
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            downloadFile();
                            Log.d("1111", "获取读写权限成功");
                        } else {
                            Log.d("1111", "获取读写部分权限成功，但部分权限未正常授予");
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Log.d("1111", "被永久拒绝授权，请手动授予读写权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(DownloadActivity.this, permissions);
                        } else {
                            Log.d("1111", "获取读写权限失败");
                        }
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == XXPermissions.REQUEST_CODE) {
            if (XXPermissions.isGranted(this, Permission.RECORD_AUDIO) &&
                    XXPermissions.isGranted(this, Permission.Group.CALENDAR)) {
                Log.d("1111", "用户已经在权限设置页授予了录音和日历权限");
            } else {
                Log.d("1111", "用户没有在权限设置页授予权限");
            }
        }
    }

    public void downloadFile() {
        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "ive" + ".exe");
        Log.d("Download", "file path :" + outputFile.getAbsolutePath());
        DownloadInfoPO apkApi = new DownloadInfoPO("https://yunpan.aliyun.com/downloads/apps/desktop/aDrive.exe?spm=aliyundrive.sign.0.0.13876c75mvMLGM&file=aDrive.exe");

//        if (outputFile.exists()) {
//            boolean deleteResult = outputFile.delete();
//            Log.d("Download", "file delete result ：" + deleteResult);
//        }

        apkApi.setId(1);
        apkApi.setUpdateProgress(true);
        apkApi.setListener(new HttpDownOnNextListener() {
            @Override
            public void onNext(Object o) {
                Log.d("1111111111111", "onNext");
            }

            @Override
            public void onStart() {
                Log.d("1111111111111", "onStart");
            }

            @Override
            public void onComplete() {
                Log.d("1111111111111", "onComplete");
            }

            @Override
            public void updateProgress(long readLength, long countLength) {
                Log.d("1111111111111", readLength + "--- updateProgress --- " + countLength + "  countLength");
            }
        });
        apkApi.setSavePath(outputFile.getAbsolutePath());
        HttpDownManager.getInstance().startDown(apkApi);
    }
}
