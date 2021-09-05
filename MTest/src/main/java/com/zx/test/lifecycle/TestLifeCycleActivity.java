package com.zx.test.lifecycle;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

        getLifecycle().addObserver(new LocationListener(this, new LocationListener.OnLocationChangeListener() {
            @Override
            public void onChanged(double latitude, double longitude) {
                Log.d("LifeCycle", "TestLifeCycleActivity");
            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

