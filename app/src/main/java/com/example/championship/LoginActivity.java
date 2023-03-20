package com.example.championship;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("MissingInflatedId")
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.tvReg).setOnClickListener(this);
        findViewById(R.id.btnSignIn).setOnClickListener(this);
        findViewById(R.id.btnProfile).setOnClickListener(this);

        setEmail();
    }

    private void setEmail() {

        SharedPreferences prefs = PreferenceManager.
                getDefaultSharedPreferences(LoginActivity.this);

        String email = prefs.getString("email", "");
        if (!email.equals("")) {
            TextView tvEmail = findViewById(R.id.tvEmail);
            tvEmail.setText(email);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSignIn:
            case R.id.btnProfile:
                EditText email = findViewById(R.id.tvEmail);
                EditText password = findViewById(R.id.tvPassword);

                checkData(email.getText().toString(), password.getText().toString());

                authorization(email.getText().toString(), password.getText().toString());
                break;

            case R.id.tvReg:
                startActivity(new Intent(LoginActivity.this,
                        RegisterActivity.class));
                break;
        }
    }

    private boolean checkData(String email, String password) {

        if (email.length() == 0 || password.length() == 0) {
            Toast.makeText(LoginActivity.this, "Заполните оба поля",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!email.contains("@")) {
            Toast.makeText(LoginActivity.this, "В логине отсутствует" +
                    "символ \"@\"", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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

                if (response.body() != null) {
                    MainActivity.user.setNickName(response.body().getNickName());
                    MainActivity.user.setAvatar(response.body().getAvatar());
                    MainActivity.user.setEmail(response.body().getEmail());
                    saveData();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Пользователь не найден",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

                Toast.makeText(LoginActivity.this, "Ошибка: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveData() {

        SharedPreferences prefs = PreferenceManager.
                getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("alreadyLogin", true);
        editor.putString("nickName", MainActivity.user.getNickName());
        editor.putString("avatar", MainActivity.user.getAvatar());
        editor.putString("email", MainActivity.user.getEmail());
        editor.apply();
    }
}