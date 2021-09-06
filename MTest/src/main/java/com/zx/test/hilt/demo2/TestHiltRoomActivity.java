package com.zx.test.hilt.demo2;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zx.test.R;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * ZhangSuiXu
 * 2021/9/6
 */
@AndroidEntryPoint
public class TestHiltRoomActivity extends AppCompatActivity {

    @Inject
    public BookDao mBookDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_test_hilt);

//        new Thread(() -> mBookDao.insert(new Book())).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Book> books = mBookDao.queryBookAll();
                Log.d("TestHiltRoomActivity",books.size() + "");
            }
        }).start();
    }
}
