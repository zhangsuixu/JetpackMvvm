package com.zx.test.room.demo1;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * ZhangSuiXu
 * 2021/9/7
 */
@Database(entities = {Student.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "test_room";

    private static MyDatabase databaseInstance;

    public static synchronized MyDatabase getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, DATABASE_NAME).build();
        }
        return databaseInstance;
    }

    public abstract StudentDao studentDao();

}
