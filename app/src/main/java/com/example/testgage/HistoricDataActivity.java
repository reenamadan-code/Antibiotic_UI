package com.example.testgage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;

import com.example.testgage.Model.Metric;
import com.example.testgage.Network.AntibioticAPIUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HistoricDataActivity extends AppCompatActivity {
    private String accessToken;
    private String user_email;
    private String deviceId;

    private final Calendar calendar = Calendar.getInstance();
    private final String DATE_FORMAT = "dd MMM yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic_data);

        //Obtain the token from the Intent's extras
        accessToken = getIntent().getStringExtra(MainActivity.EXTRA_ACCESS_TOKEN);
        user_email = getIntent().getStringExtra("USER_EMAIL");
        deviceId = getIntent().getStringExtra("DEVICE_ID");

        Toolbar toolbar = findViewById(R.id.toolbarEmailDevice);
        toolbar.setTitle("Historic Heart Rate Data");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextInputEditText fromDate = findViewById(R.id.tietFromDate);
        TextInputEditText toDate = findViewById(R.id.tietToDate);

        findViewById(R.id.historicDataSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoricDataActivity.this, ReadMetricsActivity.class));
            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String date = new SimpleDateFormat(DATE_FORMAT, Locale.US).format(calendar.getTime());
                        fromDate.setText(date);
                    }

                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(HistoricDataActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String date = new SimpleDateFormat(DATE_FORMAT, Locale.US).format(calendar.getTime());
                        toDate.setText(date);
                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(HistoricDataActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        findViewById(R.id.historicDataSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = fromDate.getText().toString();
                String to = toDate.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
                try {
                    calendar.setTime(Objects.requireNonNull(sdf.parse(from)));
                    Long fromTime = calendar.getTimeInMillis();
                    calendar.setTime(Objects.requireNonNull(sdf.parse(to)));
                    Long toTime = calendar.getTimeInMillis();

                    String metricId = String.format("%s-%s", deviceId, user_email);

                    getMetrics(metricId, String.valueOf(fromTime), String.valueOf(toTime), accessToken);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(HistoricDataActivity.this, ReadMetricsActivity.class);
            intent.putExtra(MainActivity.EXTRA_ACCESS_TOKEN, accessToken);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getMetrics(String metricId, String from, String to, String token) {
        AntibioticAPIUtils.getAPIService().getMetrics(metricId, from, to, "Bearer " + token).enqueue(new Callback<List<Metric>>() {
            @Override
            public void onResponse(Call<List<Metric>> call, Response<List<Metric>> response) {
                if (response.isSuccessful()) {
                    List<Metric> metrics = response.body();
                    ArrayList<String> dataBuilder = new ArrayList<String>();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);

                    ArrayAdapter adapter;
                    if (metrics.size() < 1) {
                        String[] holder = {"No Record Found"};
                        adapter = new ArrayAdapter<String>(HistoricDataActivity.this, R.layout.metric_entry, holder);
                    } else {
                        for (Metric metric: metrics) {
                            calendar.setTimeInMillis(Long.parseLong(metric.get_datetime()));
                            String date = sdf.format(calendar.getTime());
                            dataBuilder.add(String.format("Date: %s Heart rate: %s", date, metric.get_heart_rate()));
                        }
                        adapter = new ArrayAdapter<String>(HistoricDataActivity.this, R.layout.metric_entry, dataBuilder);
                    }
                    ListView listView = findViewById(R.id.historicListView);
                    listView.setAdapter(adapter);
                } else {
                    String[] holder = {"No Record Found"};
                    ArrayAdapter adapter = new ArrayAdapter<String>(HistoricDataActivity.this, R.layout.metric_entry, holder);
                    ListView listView = findViewById(R.id.historicListView);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Metric>> call, Throwable t) {

            }
        });
    }
}