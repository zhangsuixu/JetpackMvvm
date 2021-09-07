package com.zx.test.room.demo1;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * ZhangSuiXu
 * 2021/9/7
 */
public class TestRoomViewModel extends AndroidViewModel {

    private MyDatabase myDatabase;
    private LiveData<List<Student>> liveDataStudent;

    public TestRoomViewModel(@NonNull Application application) {
        super(application);

        myDatabase = MyDatabase.getInstance(application);
        liveDataStudent = myDatabase.studentDao().getStudentList1();
    }

    public LiveData<List<Student>> getLiveDataStudent() {
        return liveDataStudent;
    }

    public void insertData(Context context){
        new Thread(() -> {
            MyDatabase.getInstance(context).studentDao().insertStudent(new Student("哈哈","22"));
            MyDatabase.getInstance(context).studentDao().insertStudent(new Student("嘻嘻","33"));
        }).start();
    }
}
