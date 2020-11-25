package com.example.testgage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class ProfileRegisterActivity extends AppCompatActivity {

    private TextInputEditText textInputEditTextDate;
    private final Calendar calendar = Calendar.getInstance();
    private final String DATE_FORMAT = "dd MMM YYY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);

        Toolbar toolbar = findViewById(R.id.toolbarTokenLogin);
        toolbar.setTitle("Complete Profile Registration");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        textInputEditTextDate = findViewById(R.id.tietDate);

        findViewById(R.id.buttonSubmitToken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileRegisterActivity.this, HistoricDataActivity.class));
            }
        });


//        textInputEditTextDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        calendar.set(Calendar.YEAR, year);
//                        calendar.set(Calendar.MONTH, monthOfYear);
//                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                        String date = new SimpleDateFormat(DATE_FORMAT, Locale.US).format(calendar.getTime());
//                        textInputEditTextDate.setText(date);
//                    }
//
//                };
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileRegisterActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.show();
//
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}