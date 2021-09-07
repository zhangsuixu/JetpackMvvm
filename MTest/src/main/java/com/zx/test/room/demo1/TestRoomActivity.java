package com.zx.test.room.demo1;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.zx.test.R;

import java.util.List;

/**
 * ZhangSuiXu
 * 2021/9/7
 */
public class TestRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_test_room);

        TestRoomViewModel testRoomViewModel = new ViewModelProvider(this).get(TestRoomViewModel.class);
        testRoomViewModel.getLiveDataStudent().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                if(students != null){
                    Log.d("TestRoom","students size :" + students.size());
                }
            }
        });

        testRoomViewModel.insertData(this);
    }
}
