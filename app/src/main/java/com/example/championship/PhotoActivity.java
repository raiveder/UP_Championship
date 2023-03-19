package com.example.championship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgPhoto;
    private long currentTouchTime = 0;
    private boolean isDefaultImageSize = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imgPhoto = findViewById(R.id.imgPhoto);
        imgPhoto.setOnClickListener(this);
        findViewById(R.id.tvDelete).setOnClickListener(this);
        findViewById(R.id.tvClose).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvDelete:

                break;

            case R.id.tvClose:
                startActivity(new Intent(PhotoActivity.this, ProfileActivity.class));
                break;

            case R.id.imgPhoto:
                long lastTouchTime = currentTouchTime;
                currentTouchTime = System.currentTimeMillis();

                if (currentTouchTime - lastTouchTime <= 300) {
                    if (isDefaultImageSize) {
                        imgPhoto.getLayoutParams().width = imgPhoto.getWidth() * 2;
                        imgPhoto.getLayoutParams().height = imgPhoto.getHeight() * 2;
                        isDefaultImageSize = false;
                    } else {
                        imgPhoto.getLayoutParams().width = imgPhoto.getWidth() / 2;
                        imgPhoto.getLayoutParams().height = imgPhoto.getHeight() / 2;
                        isDefaultImageSize = true;
                    }

                    imgPhoto.requestLayout();
                }
                break;
        }
    }
}