package com.example.testgage;

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

import com.example.testgage.Model.User;
import com.example.testgage.Network.AntibioticAPIUtils;

public class RegisterActivity extends AppCompatActivity {

    private EditText first_name;
    private EditText last_name;
    private EditText age;
    private EditText gender;
    private Button submit;
    private Button cancel;
    private String accessToken;
    private String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Obtain the token from the Intent's extras
        accessToken = getIntent().getStringExtra(MainActivity.EXTRA_ACCESS_TOKEN);
        user_email = getIntent().getStringExtra("USER_EMAIL");

        first_name = findViewById(R.id.firstNameField);
        last_name = findViewById(R.id.lastNameField);
        age = findViewById(R.id.ageInputField);
        gender = findViewById(R.id.genderInputField);

        submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = first_name.getText().toString();
                String lastName = last_name.getText().toString();
                String genderInput = gender.getText().toString();
                String ageInput = age.getText().toString();

                createUser(user_email, firstName, lastName, genderInput, ageInput, accessToken);
            }
        });
        cancel = findViewById(R.id.cancelButtom);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_CLEAR_CREDENTIALS, true);
                startActivity(intent);
                finish();
            }
        });
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
                Intent intent = new Intent(RegisterActivity.this, HRMonitorActivity.class);
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