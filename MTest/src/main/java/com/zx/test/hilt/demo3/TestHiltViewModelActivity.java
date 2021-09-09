package com.zx.test.hilt.demo3;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.zx.test.R;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * ZhangSuiXu
 * 2021/9/6
 *
 * @AndroidEntryPoint
 * 1. 不支持带泛型的类
 * 2. 支持抽象的类
 * 3. 同时ViewModel必须通过ViewModelProvider提供
 *
 */
@AndroidEntryPoint
public class TestHiltViewModelActivity extends BaseActivity {
    public TestHiltViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_test_hilt_view_model);

        mViewModel = new ViewModelProvider(this).get(TestHiltViewModel.class);

        mViewModel.test();
    }
}
