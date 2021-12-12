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

import java.util.List;

public class DownloadActivity extends BaseActivity {

    private DownloadViewModel mViewModel;

    private ActDownloadBinding mDataBinding;

    private BaseAdapter<DownloadInfoPO, RvDownloadListItemBinding> baseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding = DataBindingUtil.setContentView(this, R.layout.act_download);
        mViewModel = new ViewModelProvider(this).get(DownloadViewModel.class);
        initView();
        requestPermissions();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

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
