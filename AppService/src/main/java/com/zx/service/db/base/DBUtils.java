package com.zx.service.db.base;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.zx.service.db.dao.DownloadInfoDao;
import com.zx.service.entity.po.DownloadInfoPO;

/**
 * ZhangSuiXu
 * 2021/9/7
 * <p>
 * Migration
 * 数据库表变更时，用于Room升级用，
 *
 * <p>
 * fallbackToDestructiveMigration()
 * 1. 当Room升级过程中没有匹配到相应的Migration，则会升级失败，通过上述方法可以在出现异常时，重新创建数据表。(重新创建旧数据旧不在了)
 */
@Database(entities = {DownloadInfoPO.class}, version = 1, exportSchema = false)
public abstract class DBUtils extends RoomDatabase {

    private static final String DATABASE_NAME = "test_room";

    private static DBUtils databaseInstance;

    public static synchronized DBUtils getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(), DBUtils.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
//                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build();
        }
        return databaseInstance;
    }

    public abstract DownloadInfoDao downloadInfoDao();
}
