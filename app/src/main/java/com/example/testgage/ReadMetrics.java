package com.example.testgage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class ReadMetrics extends AppCompatActivity {

    DatePickerDialog picker;
    EditText eText;
    Button btnGet;
    TextView tvw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_metrics);

//        tvw = findViewById(R.id.textView8);
//        eText = findViewById(R.id.editTextDate2);
//        eText.setInputType(InputType.TYPE_NULL);
//        eText.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View v) {
//                final java.util.Calendar cldr = java.util.Calendar.getInstance();
//                int day = cldr.get(java.util.Calendar.DAY_OF_MONTH);
//                int month = cldr.get(java.util.Calendar.MONTH);
//                int year = cldr.get(Calendar.YEAR);
//
//                picker = new DatePickerDialog(ReadMetrics.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        eText.setText(dayOfMonth + "/" +(month +1) + "/" + year);
//                    }
//                }, year, month, day);
//                picker.show();
//            }
//        });
//
//
//        btnGet = findViewById(R.id.bt1);
//        btnGet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvw.setText("Date: " + eText.getText());
//            }
//        });


        Toolbar toolbar = findViewById(R.id.toolbarHome);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

    }
}