package com.zx.test.hilt;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zx.test.R;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * ZhangSuiXu
 * 2021/9/6
 */
@AndroidEntryPoint
public class TestHiltActivity extends AppCompatActivity {

    @Inject
    public NetDataSource mNetDataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_test_hilt);

        mNetDataSource.test();
    }
}
