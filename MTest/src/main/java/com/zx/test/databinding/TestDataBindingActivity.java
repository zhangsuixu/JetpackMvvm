package com.zx.test.databinding;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.zx.test.R;

/**
 * ZhangSuiXu
 * 2021/9/9
 */
public class TestDataBindingActivity extends AppCompatActivity {

    public ActTestDatabindingBinding mViewDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewDataBinding = DataBindingUtil.bind(View.inflate(this, R.layout.act_test_databinding, null));
        setContentView(mViewDataBinding.getRoot());

        mViewDataBinding.setBook(new Book("biaoti","zuozhe",1));
    }
}
