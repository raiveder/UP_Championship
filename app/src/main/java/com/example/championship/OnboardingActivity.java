package com.example.championship;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

@SuppressLint("NonConstantResourceId")
public class OnboardingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        findViewById(R.id.btnEntry).setOnClickListener(this);
        findViewById(R.id.tvRegister).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnEntry:
                startActivity(new Intent(OnboardingActivity.this,
                        LoginActivity.class));
                break;

            case R.id.tvRegister:
                startActivity(new Intent(OnboardingActivity.this,
                        RegisterActivity.class));
                break;
        }
    }
}