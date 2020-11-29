package com.example.testgage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.management.ManagementException;
import com.auth0.android.management.UsersAPIClient;
import com.auth0.android.result.UserProfile;
import com.example.testgage.Model.Metric;
import com.example.testgage.Model.User;
import com.example.testgage.Network.AntibioticAPIUtils;
import com.example.testgage.Network.AntibioticCallbacks;
import java.util.List;

public class HRMonitorActivity extends AppCompatActivity {
    private AuthenticationAPIClient authenticationAPIClient;
    private UsersAPIClient usersClient;
    private UserProfile userProfile;
    private String accessToken;
    private String deviceId;

    private EditText personName;
    private EditText email;
    private EditText metricTime;
    private EditText heart_rate;
    private TextView gender;
    private TextView age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrmonitor);

        personName = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextTextEmail);
        age = findViewById(R.id.ageField);
        gender = findViewById(R.id.genderField);
        metricTime = findViewById(R.id.metricTime);
        heart_rate = findViewById(R.id.heartRateField);

        Button logout = findViewById(R.id.logOutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        Button historic = findViewById(R.id.historicButton);
        historic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HRMonitorActivity.this, HistoricActivity.class);
                intent.putExtra(MainActivity.EXTRA_ACCESS_TOKEN, accessToken);
                intent.putExtra("USER_EMAIL", userProfile.getEmail());
                intent.putExtra("DEVICE_ID", deviceId);
                startActivity(intent);
                finish();
            }
        });

        //Obtain the token from the Intent's extras
        accessToken = getIntent().getStringExtra(MainActivity.EXTRA_ACCESS_TOKEN);

        Log.i("[INFO]", accessToken);

        Auth0 auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);
        authenticationAPIClient = new AuthenticationAPIClient(auth0);
        usersClient = new UsersAPIClient(auth0, accessToken);
        getProfile(accessToken);
    }

    private void logout() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_CLEAR_CREDENTIALS, true);
        startActivity(intent);
        finish();
    }

    private void refreshScreenInformation() {
        email.setText(userProfile.getEmail());
        getUser(userProfile.getEmail(), accessToken, new AntibioticCallbacks() {
            @Override
            public void onSuccess(@NonNull String data) {
                if (data != "User does not exist") {
                    String name = String.format("%s %s", data.split("-")[0], data.split("-")[1]);
                    personName.setText(name);
                    gender.setText(data.split("-")[2]);
                    age.setText(data.split("-")[3]);
                    deviceId = data.split("-")[4];

                    String metricId = String.format("%s-%s", deviceId, userProfile.getEmail());
                    getMetric(metricId, accessToken);
                } else {
                    Intent intent = new Intent(HRMonitorActivity.this, RegisterActivity.class);
                    intent.putExtra(MainActivity.EXTRA_ACCESS_TOKEN, accessToken);
                    intent.putExtra("USER_EMAIL", userProfile.getEmail());
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }
        });
    }

    private void getProfile(String accessToken) {
        authenticationAPIClient.userInfo(accessToken)
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(UserProfile userinfo) {
                        usersClient.getProfile(userinfo.getId())
                                .start(new BaseCallback<UserProfile, ManagementException>() {
                                    @Override
                                    public void onSuccess(UserProfile profile) {
                                        userProfile = profile;
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                refreshScreenInformation();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(ManagementException error) {
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                Toast.makeText(HRMonitorActivity.this, "User Profile Request Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                    }

                    @Override
                    public void onFailure(AuthenticationException error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(HRMonitorActivity.this, "User Info Request Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    private void getUser(String email, String token, @Nullable final AntibioticCallbacks callbacks) {
        AntibioticAPIUtils.getAPIService().getUser(email, "Bearer " + token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();

                    if (callbacks != null) {
                        Log.i("INFO", "getUser" + user.toString());
                        callbacks.onSuccess(String.format("%s-%s-%s-%s-%S", user.get_first_name(), user.get_last_name(), user.get_age(), user.get_gender(), user.get_deviceId()));
                    }
                } else {
                    if (callbacks != null) {
                        callbacks.onSuccess("User does not exist");
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (callbacks != null)
                    callbacks.onError(t);
            }
        });
    }

    private void getMetric(String metricId, String token) {
        AntibioticAPIUtils.getAPIService().getMetrics(metricId, null, null, "Bearer " + token).enqueue(new Callback<List<Metric>>() {
            @Override
            public void onResponse(Call<List<Metric>> call, Response<List<Metric>> response) {
                if (response.isSuccessful()) {
                    List<Metric> metrics = response.body();

                    for (Metric metric : metrics) {
                        metricTime.setText(metric.get_datetime());
                        heart_rate.setText(metric.get_heart_rate());
                    }
                } else {
                    metricTime.setText("No data available");
                    heart_rate.setText("No data available");
                }
            }

            @Override
            public void onFailure(Call<List<Metric>> call, Throwable t) {

            }
        });
    }
}