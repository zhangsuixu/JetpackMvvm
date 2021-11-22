package com.zx.common.utils;

import java.io.File;

/**
 * ZhangSuiXu
 * 2021/11/22
 */
public class FileUtils {

    public static boolean mkDir(File file) {
        if(file == null || file.getParentFile() == null){
            return false;
        }

        if (!file.getParentFile().exists())
            return file.getParentFile().mkdirs();

        return false;
    }
}
