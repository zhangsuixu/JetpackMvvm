package com.zx.test.autoservice.demo1;

import android.util.Log;

import com.google.auto.service.AutoService;

/**
 * ZhangSuiXu
 * 2021/9/6
 */
@AutoService(ITestAutoService.class)
public class TestAutoService implements ITestAutoService {

    @Override
    public void test() {
        Log.d("TestAutoService","TestAutoService.test()");
    }
}
