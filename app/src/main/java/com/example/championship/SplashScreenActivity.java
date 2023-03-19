package com.example.championship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences prefs = PreferenceManager.
                getDefaultSharedPreferences(SplashScreenActivity.this);

        if (prefs.getBoolean("alreadyLogin", false)) {
            MainActivity.Name = prefs.getString("nickName", "");
            MainActivity.Avatar = prefs.getString("avatar", "");

            new Handler().postDelayed(() -> startActivity(new Intent(
                    SplashScreenActivity.this, MainActivity.class)), 3500);
        } else {
            new Handler().postDelayed(() -> startActivity(new Intent(
                            SplashScreenActivity.this, OnboardingActivity.class)),
                    3500);
        }
    }
}