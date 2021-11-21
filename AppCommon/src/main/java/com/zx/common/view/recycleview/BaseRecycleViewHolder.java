package com.zx.common.view.recycleview;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseRecycleViewHolder<VB extends ViewDataBinding> extends RecyclerView.ViewHolder {

    protected VB mBindings;
    protected View itemView;

    public BaseRecycleViewHolder(@NonNull VB bindings) {
        super(bindings.getRoot());
        this.itemView = bindings.getRoot();
        this.mBindings = bindings;
    }

    public VB getBindings() {
        return mBindings;
    }
}

