package com.zx.test.hilt.demo2;

import android.content.Context;

import androidx.room.Room;

import com.zx.test.hilt.TestHiltApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

/**
 * ZhangSuiXu
 * 2021/9/6
 */
@InstallIn(SingletonComponent.class)
@Module
public class BookModule {

    @Provides
    @Singleton
    public TestDataBase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, TestDataBase.class, "TestDataBase").build();
    }

    @Provides
    public BookDao getBookDao(TestDataBase dataBase){
        return dataBase.getBookDao();

    }
}
