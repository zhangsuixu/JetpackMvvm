package com.zx.test.room.demo1;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
@Database(entities = {Student.class}, version = 3, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "test_room";

    private static MyDatabase databaseInstance;

    public static synchronized MyDatabase getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
//                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build();
        }
        return databaseInstance;
    }

    public abstract StudentDao studentDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE student ADD address varchar(110)");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //创建临时表
            database.execSQL("CREATE TABLE temp_Student(id INTEGER PRIMARY KEY NOT NULL,name TEXT,age TEXT)");
            //复制旧表内容到新表
            database.execSQL("INSERT INTO temp_Student (id,name,age) SELECT id,name,age FROM Student");
            //删除旧表
            database.execSQL("DROP TABLE Student");
            //将临时表temp_Student重命名为Student
            database.execSQL("ALTER TABLE temp_student RENAME TO Student");
        }
    };
}
