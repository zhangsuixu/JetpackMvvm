package com.zx.test.hilt.demo3;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * ZhangSuiXu
 * 2021/9/8
 */
public abstract class BaseActivity<VB extends ViewDataBinding> extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
//
//        try {
//            Class<VM> clazz = (Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//            mViewModel = new ViewModelProvider(this, factory).get(clazz);
//        } catch (Exception e) {
//            Log.d("TestHiltViewModel", e.getMessage());
//        }


    }
}
