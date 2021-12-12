package com.download;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.function.LongUnaryOperator;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("BaseActivity","onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        Log.d("BaseActivity","onStart()");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d("BaseActivity","onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d("BaseActivity","onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("BaseActivity","onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("BaseActivity","onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("BaseActivity","onDestroy()");
        super.onDestroy();
    }
}
