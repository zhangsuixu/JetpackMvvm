package com.download;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.download.databinding.ActDownloadBinding;
import com.download.databinding.RvDownloadListItemBinding;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.zx.common.view.recycleview.BaseAdapter;
import com.zx.common.view.recycleview.BaseRecycleViewHolder;
import com.zx.service.entity.po.DownloadInfoPO;
import com.zx.service.net.download.HttpDownManager;
import com.zx.service.net.download.listener.HttpDownOnNextListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {

    private static final String URL1 = "https://979c6349b74fefbed4a796a88166e242.dlied1.cdntips.net/download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_2.apk?mkey=61999edc716ff1a2&f=0000&cip=113.111.215.87&proto=https";
    private static final String URL2 = "http://125.77.163.157:49155/imtt.dd.qq.com/16891/apk/C63F38F0812C0B99C799F9A05AB5F2D7.apk?mkey=61999f1863af9f294d84e0fc9da36b88&arrive_key=41178242878&fsname=com.outfit7.mytalkingtomfree_23877.apk&csr=81e7&cip=113.111.215.87&proto=http";
    private static final String URL3 = "http://120.240.53.131:49155/imtt.dd.qq.com/16891/apk/1F8AB3A3BDD307B8C09A6DA093FA2ADB.apk?mkey=619adca2637089d24b170cfc8335d68d&arrive_key=149921548873&fsname=com.tencent.map_9.17.0_1416.apk&csr=81e7&cip=223.104.67.31&proto=http";
    private static final String URL4 = "http://183.233.158.6:49155/imtt.dd.qq.com/16891/apk/0357E9448E8F1E368D3C5C8CC1EE1C3F.apk?mkey=619adc85637089d29f82e0fc069ecf42&arrive_key=81152523505&fsname=tv.pps.mobile_10.7.0_81258.apk&csr=81e7&cip=223.104.67.31&proto=http";
    private static final String URL5 = "http://183.233.158.11:49155/imtt.dd.qq.com/16891/apk/2606F12A6FFED332BFC6AD2FBB01ADB5.apk?mkey=619adc75637089d2e4a23ffc0b9ecf42&arrive_key=35107980695&fsname=com.tencent.qqmusic_11.0.1.6_2520.apk&csr=81e7&cip=223.104.67.31&proto=http";
    private static final String URL6 = "http://120.240.53.132:49155/imtt.dd.qq.com/16891/apk/EB6E23DA98809B0E8E6A7C2040E7D516.apk?mkey=619adc76637089d2741b0cfc8435d68d&arrive_key=77545809168&fsname=dopool.player_8.6.6_503.apk&csr=81e7&cip=223.104.67.31&proto=http";

    private ActDownloadBinding mDataBinding;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding = DataBindingUtil.setContentView(this, R.layout.act_download);
        initView();
        requestPermissions();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mDataBinding.rvDownloadList.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        BaseAdapter<DownloadInfoPO, RvDownloadListItemBinding> baseAdapter = new BaseAdapter<DownloadInfoPO, RvDownloadListItemBinding>() {
            @Override
            protected int setupResId() {
                return R.layout.rv_download_list_item;
            }

            @Override
            protected void bindData(@NonNull BaseRecycleViewHolder<RvDownloadListItemBinding> holder, DownloadInfoPO data, int position) {
                holder.getBindings().download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.setListener(new HttpDownOnNextListener() {
                            @Override
                            public void onNext(Object o) {
                                Log.d("DownloadActivity", "onNext");
                            }

                            @Override
                            public void onStart() {
                                Log.d("DownloadActivity", "onStart");
                            }

                            @Override
                            public void onComplete() {
                                Log.d("DownloadActivity", "onComplete");
                            }

                            @Override
                            public void updateProgress(long readLength, long countLength) {
                                DownloadActivity.this.runOnUiThread(() -> {
                                    Log.d("DownloadActivity", readLength + "--- updateProgress --- " + countLength + "  countLength");
                                    holder.getBindings().progress.setText(readLength + "---" + countLength);
                                    data.setReadLength(readLength);
                                    data.setCountLength(countLength);
                                });
                            }
                        });
                        HttpDownManager.getInstance().startDown(data);
                    }
                });

                holder.getBindings().pause.setOnClickListener(v -> {
                    HttpDownManager.getInstance().stopDown(data);
                });

            }
        };
        baseAdapter.setData(getData());
        mDataBinding.rvDownloadList.setAdapter(baseAdapter);
    }

    private List<DownloadInfoPO> getData() {
        ArrayList<DownloadInfoPO> objects = new ArrayList<>();

        File outputFile = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "a1" + ".apk");
        Log.d("DownloadActivity", "file path :" + outputFile.getAbsolutePath());
        DownloadInfoPO apkApi = new DownloadInfoPO(URL1);
        apkApi.setId(1);
        apkApi.setSavePath(outputFile.getAbsolutePath());
        objects.add(apkApi);

        outputFile = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "a2" + ".apk");
        Log.d("DownloadActivity", "file path :" + outputFile.getAbsolutePath());
        apkApi = new DownloadInfoPO(URL2);
        apkApi.setId(2);
        apkApi.setSavePath(outputFile.getAbsolutePath());
        objects.add(apkApi);

        outputFile = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "a3" + ".apk");
        Log.d("DownloadActivity", "file path :" + outputFile.getAbsolutePath());
        apkApi = new DownloadInfoPO(URL3);
        apkApi.setId(3);
        apkApi.setSavePath(outputFile.getAbsolutePath());
        objects.add(apkApi);

        outputFile = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "a4" + ".apk");
        Log.d("DownloadActivity", "file path :" + outputFile.getAbsolutePath());
        apkApi = new DownloadInfoPO(URL4);
        apkApi.setId(4);
        apkApi.setSavePath(outputFile.getAbsolutePath());
        objects.add(apkApi);

        outputFile = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "a5" + ".apk");
        Log.d("DownloadActivity", "file path :" + outputFile.getAbsolutePath());
        apkApi = new DownloadInfoPO(URL5);
        apkApi.setId(5);
        apkApi.setSavePath(outputFile.getAbsolutePath());
        objects.add(apkApi);

        outputFile = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "a6" + ".apk");
        Log.d("DownloadActivity", "file path :" + outputFile.getAbsolutePath());
        apkApi = new DownloadInfoPO(URL6);
        apkApi.setId(6);
        apkApi.setSavePath(outputFile.getAbsolutePath());
        objects.add(apkApi);

        return objects;
    }

    public void downloadFile() {

    }

    private void requestPermissions() {
        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.Group.STORAGE)
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
            if (XXPermissions.isGranted(this, Permission.Group.STORAGE)) {
                Log.d("1111", "用户已经在权限设置页授予了读写权限");
            } else {
                Log.d("1111", "用户没有在权限设置页授予权限");
            }
        }
    }


}
