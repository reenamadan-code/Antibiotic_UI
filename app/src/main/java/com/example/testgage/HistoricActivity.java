package com.example.testgage;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.testgage.Model.Metric;
import com.example.testgage.Network.AntibioticAPIUtils;

import java.util.List;

public class HistoricActivity extends AppCompatActivity {
    private TextView historicList;
    private EditText fromField;
    private EditText toField;
    private Button getHistoric;
    private Button backButton;

    private String accessToken;
    private String user_email;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);

        //Obtain the token from the Intent's extras
        accessToken = getIntent().getStringExtra(MainActivity.EXTRA_ACCESS_TOKEN);
        user_email = getIntent().getStringExtra("USER_EMAIL");
        deviceId = getIntent().getStringExtra("DEVICE_ID");

        historicList = findViewById(R.id.historicList);
        fromField = findViewById(R.id.fromField);
        toField = findViewById(R.id.toField);

        getHistoric = findViewById(R.id.getHistoric);
        getHistoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = fromField.getText().toString();
                String to = toField.getText().toString();
                String metricId = String.format("%s-%s", deviceId, user_email);

                getMetrics(metricId, from, to, accessToken);
            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoricActivity.this, HRMonitorActivity.class);
                intent.putExtra(MainActivity.EXTRA_ACCESS_TOKEN, accessToken);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getMetrics(String metricId, String from, String to, String token) {
        AntibioticAPIUtils.getAPIService().getMetrics(metricId, from, to, "Bearer " + token).enqueue(new Callback<List<Metric>>() {
            @Override
            public void onResponse(Call<List<Metric>> call, Response<List<Metric>> response) {
                if (response.isSuccessful()) {
                    List<Metric> metrics = response.body();
                    StringBuilder textInput = new StringBuilder();

                    for (Metric metric : metrics) {
                        textInput.append(String.format("Time: %s, Heart rate: %s", metric.get_datetime(), metric.get_heart_rate())).append("\n");
                    }

                    historicList.setText(textInput.toString());
                } else {
                    historicList.setText("No data available");
                }
            }

            @Override
            public void onFailure(Call<List<Metric>> call, Throwable t) {

            }
        });
    }
}