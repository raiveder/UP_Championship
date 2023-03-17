package com.example.championship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        TextView tv = findViewById(R.id.reg);
        tv.setOnClickListener(this);

        Button btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);

        Button btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSignIn:
            case R.id.btnProfile:
                EditText email = findViewById(R.id.email);
                EditText pass = findViewById(R.id.pass);

                if (email.getText().length() == 0 || pass.getText().length() == 0) {
                    Toast.makeText(AuthorizationActivity.this, "Заполните оба поля",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!email.getText().toString().contains("@")) {
                    Toast.makeText(AuthorizationActivity.this, "В логине отсутствует" +
                            "символ \"@\"", Toast.LENGTH_SHORT).show();
                    return;
                }

                authorization(email.getText().toString(), pass.getText().toString());
                break;

            case R.id.reg:
                startActivity(new Intent(AuthorizationActivity.this,
                        RegActivity.class));
                break;
        }
    }

    private void authorization(String email, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Users user = new Users(email, password);

        Call<Users> call = retrofitAPI.getToken(user);
        call.enqueue(new Callback<Users>() {

            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                new Handler().postDelayed(() -> startActivity(new Intent(
                                AuthorizationActivity.this, MainMenuActivity.class)),
                        500);

                MainMenuActivity.Name = response.body().getNickName();
                MainMenuActivity.Avatar = response.body().getAvatar();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

                Toast.makeText(AuthorizationActivity.this, "Ошибка: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}