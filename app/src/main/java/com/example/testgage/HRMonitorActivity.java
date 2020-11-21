package com.example.testgage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.management.ManagementException;
import com.auth0.android.management.UsersAPIClient;
import com.auth0.android.result.UserProfile;

public class HRMonitorActivity extends AppCompatActivity {
    private AuthenticationAPIClient authenticationAPIClient;
    private UsersAPIClient usersClient;
    private UserProfile userProfile;

    private EditText holder;
    private EditText personName;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrmonitor);

        personName = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextTextEmail);

        Button logout = findViewById(R.id.logOutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        //Obtain the token from the Intent's extras
        String accessToken = getIntent().getStringExtra(MainActivity.EXTRA_ACCESS_TOKEN);

        holder = findViewById(R.id.editTextDate);
        Log.i("[INFO]", accessToken);
        holder.setText(accessToken);

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
        personName.setText(userProfile.getName());
        email.setText(userProfile.getEmail());
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
                                                Log.e("[ERROR]", error.toString());
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
}