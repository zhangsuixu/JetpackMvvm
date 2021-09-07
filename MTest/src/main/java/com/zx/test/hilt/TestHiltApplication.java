package com.zx.test.hilt;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

/**
 * ZhangSuiXu
 * 2021/9/6
 *
 *  1. 所有使用Hilt的应用都必须包含一个带有 @HiltAndroidApp 注释的 Application 类，
 *     用于【触发 Hilt 的代码生成操作】，生成的代码包括应用的一个基类，该基类充当应用级依赖项容器。
 *
 *
 */
@HiltAndroidApp
public class TestHiltApplication extends Application {
}
