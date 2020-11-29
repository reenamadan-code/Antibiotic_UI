package com.example.testgage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.testgage.Model.User;
import com.example.testgage.Network.AntibioticAPIUtils;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileRegisterActivity extends AppCompatActivity {
    private String accessToken;
    private String user_email;

    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextInputEditText age;
    private TextInputEditText gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);

        //Obtain the token from the Intent's extras
        accessToken = getIntent().getStringExtra(MainActivity.EXTRA_ACCESS_TOKEN);
        user_email = getIntent().getStringExtra("USER_EMAIL");

        firstName = findViewById(R.id.tietFirstName);
        lastName = findViewById(R.id.tietLastName);
        age = findViewById(R.id.tietAge);
        gender = findViewById(R.id.tietGender);

        Toolbar toolbar = findViewById(R.id.toolbarTokenLogin);
        toolbar.setTitle("Complete Profile Registration");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        findViewById(R.id.regSubmitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_Name = firstName.getText().toString();
                String last_Name = lastName.getText().toString();
                String genderInput = gender.getText().toString();
                String ageInput = age.getText().toString();

                createUser(user_email, first_Name, last_Name, genderInput, ageInput, accessToken);
            }
        });

        findViewById(R.id.regCancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileRegisterActivity.this, LoginActivity.class);
                intent.putExtra(MainActivity.EXTRA_CLEAR_CREDENTIALS, true);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createUser(String email, String first_name, String last_name, String gender, String age,  String token) {
        User user = new User();
        user.set_email(email);
        user.set_first_name(first_name);
        user.set_last_name(last_name);
        user.set_age(age);
        user.set_gender(gender);

        AntibioticAPIUtils.getAPIService().createUser(user, "Bearer " + token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("[INFO] createUser", response.toString());
                Intent intent = new Intent(ProfileRegisterActivity.this, ReadMetricsActivity.class);
                intent.putExtra(MainActivity.EXTRA_ACCESS_TOKEN, accessToken);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("[ERROR] CreateUser", t.toString());
            }
        });
    }
}