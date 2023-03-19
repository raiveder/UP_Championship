package com.example.championship;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById(R.id.tvExit).setOnClickListener(this);

        new DownloadImages(findViewById(R.id.imgAvatar), findViewById(R.id.pbWait)).
                execute(MainActivity.user.getAvatar());
        TextView tvName = findViewById(R.id.tvName);
        tvName.setText(MainActivity.user.getNickName());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvExit:
                saveEmail();
                startActivity(new Intent(ProfileActivity.this,
                        LoginActivity.class));
                break;
        }
    }

    private void saveEmail() {

        SharedPreferences prefs = PreferenceManager.
                getDefaultSharedPreferences(ProfileActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("alreadyLogin", false);
        editor.putString("email", MainActivity.user.getEmail());
        editor.apply();
    }
}