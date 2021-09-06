package com.zx.test.hilt.demo1;

import android.util.Log;

import javax.inject.Inject;

/**
 * ZhangSuiXu
 * 2021/9/6
 */
public class NetDataSource {

    @Inject
    public NetDataSource() {
    }

    public void test(){
        Log.d("TestHilt","NetDataSource.test()");
    }
}
