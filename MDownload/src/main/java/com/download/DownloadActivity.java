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
import com.zx.common.constant.DownState;
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
                        if (data.getState() == DownState.START) {
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
                                    DownloadActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("DownloadActivity", readLength + "--- updateProgress --- " + countLength + "  countLength");
                                            holder.getBindings().progress.setText(readLength + "---" + countLength);
                                        }
                                    });
                                }
                            });
                            HttpDownManager.getInstance().startDown(data);
                        }
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
        apkApi.setSavePath(outputFile.getAbsolutePath());
        objects.add(apkApi);

        outputFile = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "a2" + ".apk");
        Log.d("DownloadActivity", "file path :" + outputFile.getAbsolutePath());
        apkApi = new DownloadInfoPO(URL2);
        apkApi.setSavePath(outputFile.getAbsolutePath());
        objects.add(apkApi);

        return objects;
    }

    public void downloadFile() {
//        Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
//                DBUtils.getInstance(DownloadActivity.this).downloadInfoDao().insert(apkApi);
//                List<DownloadInfoPO> downloadInfoPOS = DBUtils.getInstance(DownloadActivity.this).downloadInfoDao().queryDownloadInfoList();
//                if (downloadInfoPOS != null) {
//                    Log.d("11111111111111", downloadInfoPOS.size() + "测试测试");
//                }
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
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
