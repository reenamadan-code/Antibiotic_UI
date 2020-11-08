package com.example.testgage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity<pulic> extends AppCompatActivity {

    private Log log;
    private String TAG;

    public void clickbtn(View view) {
        Log.i(TAG, "clickbtn: this is a message");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}