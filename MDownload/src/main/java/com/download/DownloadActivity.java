package com.download;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class DownloadActivity extends BaseActivity {

    private DownloadViewModel mViewModel;

    private ActDownloadBinding mDataBinding;

    private BaseAdapter<DownloadInfoPO, RvDownloadListItemBinding> baseAdapter;

    private final List<User> mRunning = new ArrayList<>();
    private final LinkedBlockingQueue<User> mWaitingQueue = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<User> mRunningQueue = new LinkedBlockingQueue<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding = DataBindingUtil.setContentView(this, R.layout.act_download);
        mViewModel = new ViewModelProvider(this).get(DownloadViewModel.class);
        initView();
        requestPermissions();

//        mQueue.add(new User("哇哈哈", "随机数2"));
//
//        Log.d("测试测试 ：", mQueue.contains(new User("哇哈哈", "随机数3")) + "-----1");
//        Log.d("测试测试 ：", mQueue.contains(new User("哇哈", "随机数2")) + "-----2");
//
//        mQueue.remove(new User("哇哈哈", "随机数3"));
//        Log.d("测试测试 ：", mQueue.contains(new User("哇哈哈", "随机数3")) + "-----3");

        test();
    }

    private void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 30; i++) {
                        mWaitingQueue.add(new User("线程1存 ：" + i));
                        Log.d("测试测试 ：", "线程1存 ：" + i);
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("测试测试 ：", "发生中断");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 30; i++) {
                        mWaitingQueue.add(new User("线程2存 ：" + i));
                        Log.d("测试测试 ：", "线程2存 ：" + i);
                        Thread.sleep(150);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("测试测试 ：", "发生中断");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (isExit) {
                            return;
                        }

                        if (mRunningQueue.size() < 3) {
                            User take = mWaitingQueue.take();
                            if (take != null) {
                                Log.d("测试测试 ：", "向运行队列添加一个数据" + take.name);
                                mRunningQueue.add(take);
                            }
                        }
                    } catch (InterruptedException e) {
                        Log.d("测试测试 ：", "发生中断");
                    }

                }
            }
        }).start();

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                    try {
                        if (isExit) {
                            return;
                        }

                        if (mRunningQueue.size() > 0) {
                            Log.d("测试测试 ：", "移除数据   移除次数 ：" + (++count));
                            mRunningQueue.remove();
                        }
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.d("测试测试 ：", "发生中断");
                    }
            }
        });
        mThread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {//
                while (true) {
                    if (mRunningQueue.contains(new User("线程2存 ：5"))) {
                        Log.d("测试测试 ：", "查询到指定项" + mRunningQueue.size() + "---------------------------------------" + mWaitingQueue.size());
                        mRunningQueue.clear();
                        mWaitingQueue.clear();
                        Log.d("测试测试 ：", "查询到指定项" + mRunningQueue.size() + "---------------------------------------" + mWaitingQueue.size());
                        return;
                    }
                }
            }
        }).start();
    }

    private volatile boolean isExit = false;

    @Override
    protected void onPause() {
        super.onPause();
        isExit = true;
    }

    private int count = 0;

    private Thread mThread;

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mDataBinding.rvDownloadList.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        baseAdapter = new BaseAdapter<DownloadInfoPO, RvDownloadListItemBinding>() {
            @Override
            protected int setupResId() {
                return R.layout.rv_download_list_item;
            }

            @Override
            protected void bindData(@NonNull BaseRecycleViewHolder<RvDownloadListItemBinding> holder, DownloadInfoPO data, int position) {
                holder.getBindings().setDownloadInfoPO(mViewModel.getData().get(position));

                holder.getBindings().download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpDownManager.getInstance().startDown(data);
                    }
                });

                holder.getBindings().pause.setOnClickListener(v -> {
                    HttpDownManager.getInstance().stopDown(data);
                });
            }
        };
        baseAdapter.setData(mViewModel.getData());
        mDataBinding.rvDownloadList.setAdapter(baseAdapter);
    }

    private void requestPermissions() {
        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.Group.STORAGE)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            Log.d("Permissions", "获取读写权限成功");
                        } else {
                            Log.d("Permissions", "获取读写部分权限成功，但部分权限未正常授予");
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Log.d("Permissions", "被永久拒绝授权，请手动授予读写权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(DownloadActivity.this, permissions);
                        } else {
                            Log.d("Permissions", "获取读写权限失败");
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == XXPermissions.REQUEST_CODE) {
            if (XXPermissions.isGranted(this, Permission.Group.STORAGE)) {
                Log.d("Permissions", "用户已经在权限设置页授予了读写权限");
            } else {
                Log.d("Permissions", "用户没有在权限设置页授予权限");
            }
        }
    }


}
