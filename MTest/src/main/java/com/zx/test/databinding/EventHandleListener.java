package com.zx.test.databinding;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * ZhangSuiXu
 * 2021/9/9
 */
public class EventHandleListener {
    private Context context;

    public EventHandleListener(Context context) {
        this.context = context;
    }

    public void onButtonClicked(View view) {
        Toast.makeText(context, "I am clicked", Toast.LENGTH_SHORT).show();
    }
}
