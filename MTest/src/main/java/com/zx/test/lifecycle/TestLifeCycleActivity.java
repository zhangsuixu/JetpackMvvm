package com.zx.test.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zx.test.R;
import com.zx.test.lifecycle.service.TestLifeCycleService;

/**
 * ZhangSuiXu
 * 2021/9/5
 * <p>
 * 测试LifeCycle(适用Activity和Fragment)
 */
public class TestLifeCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_test_lifecycle);

//        getLifecycle().addObserver(new LocationListener(this, (latitude, longitude) -> Log.d("LifeCycle", "TestLifeCycleActivity")));

        findViewById(R.id.start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LifeCycleService","StartService");
                TestLifeCycleActivity.this.startService(new Intent(TestLifeCycleActivity.this, TestLifeCycleService.class));
            }
        });

        findViewById(R.id.stop_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LifeCycleService","stopService");
                TestLifeCycleActivity.this.stopService(new Intent(TestLifeCycleActivity.this, TestLifeCycleService.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

