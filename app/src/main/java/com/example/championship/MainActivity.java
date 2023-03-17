package com.example.championship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String test = "dsa";
        new Handler().postDelayed(() -> startActivity(new Intent(MainActivity.this,
                OnboardingActivity.class)), 3500);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getBoolean("AlreadyAuthorization")) {
            new Handler().postDelayed(() -> startActivity(new Intent(MainActivity.this,
                    OnboardingActivity.class)), 3500);
        } else {
            new Handler().postDelayed(() -> startActivity(new Intent(MainActivity.this,
                    MainMenuActivity.class)), 3500);
        }
    }
}