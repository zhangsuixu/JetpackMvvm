package com.zx.test.autoservice.demo1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zx.test.R;

import java.util.ServiceLoader;

/**
 * ZhangSuiXu
 * 2021/9/6
 */
public class TestAutoServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_test_auto_service);

        ServiceLoader.load(ITestAutoService.class).iterator().next().test();
    }
}
