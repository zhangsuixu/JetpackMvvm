package com.zx.test.hilt.demo2;

import android.content.Context;
import android.util.Log;

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
 *
 * SingletonComponent           生命周期和应用周期一致
 * ActivityComponent            生命周期与Activity相关联
 * ActivityRetainedComponent
 * FragmentComponent
 * ServiceComponent
 * ViewComponent
 * ViewModelComponent
 * ViewWithFragmentComponent
 *
 */
@InstallIn(SingletonComponent.class)
@Module
public class BookModule {

    @Provides
    @Singleton
    public TestDataBase provideDatabase(@ApplicationContext Context context) {
        Log.d("TestHilt","provideDatabase()");
        return Room.databaseBuilder(context, TestDataBase.class, "TestDataBase").build();
    }

    @Provides
    public BookDao getBookDao(TestDataBase dataBase){
        Log.d("TestHilt","getBookDao()");
        return dataBase.getBookDao();

    }
}
