package com.zx.test.viewmodel;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.zx.test.R;

/**
 * ZhangSuiXu
 * 2021/9/5
 * <p>
 * 当手动切换横竖屏时，计时器并未停止。即切换横竖屏ViewModel并未销毁
 * <p>
 * ViewModel实际以HashMap的方式存储起来 ( key : DEFAULT_KEY + ":" + canonicalName  value : modelClass)
 * 即ViewModel和Activity并无直接关联，所以界面切换时，ViewModel是同一个。
 * 所以不应该让ViewModel持有Context，防止Activity无法销毁而内存泄露。如果一定要在ViewModel中使用Context，可以使用AndroidViewModel
 * <p>
 * ViewModel与onSaveInstanceState()方法区别
 * onSaveInstanceState()方法的少量的，能支持序列化的数据，而ViewModel没限制
 * onSaveInstanceState()支持数据持久化，而ViewModel不支持
 */
public class TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_timer);

        initComponent();
    }

    private void initComponent() {
        final TextView tvTime = findViewById(R.id.tv_time);

        TimerViewModel timerViewModel = new ViewModelProvider(this).get(TimerViewModel.class);

        timerViewModel.setOnTimerChangeListener(new TimerViewModel.OnTimerChangeListener() {
            @Override
            public void onTimerChanged(int second) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText("TIME :" + second);
                    }
                });
            }
        });

        timerViewModel.startTiming();
    }
}
