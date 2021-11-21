package com.zx.common.view.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public abstract class BaseAdapter<T, VB extends ViewDataBinding> extends RecyclerView.Adapter<BaseRecycleViewHolder> {

    protected List<T> mDataList;
    protected Context mContext;

    public void setData(List<T> dataList) {
        mDataList = dataList;
    }

    @NonNull
    @Override
    public BaseRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        VB dataBindings = DataBindingUtil.inflate(inflater, setupResId(), parent, false);
        BaseRecycleViewHolder holder = new BaseRecycleViewHolder(dataBindings);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecycleViewHolder holder, int position) {
        final T data = mDataList.get(position);
        bindData(holder, data, position);
    }

    @Override
    public int getItemCount() {
        if (null == mDataList) {
            return 0;
        } else {
            return mDataList.size();
        }
    }

    /**
     * 设置子项资源文件
     *
     * @return
     */
    protected abstract int setupResId();

    /**
     * 数据绑定
     *
     * @param holder
     * @param data
     */
    protected abstract void bindData(@NonNull BaseRecycleViewHolder<VB> holder, T data, int position);

}

