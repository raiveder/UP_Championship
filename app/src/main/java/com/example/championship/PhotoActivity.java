package com.example.championship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgPhoto;
    private long currentTouchTime = 0;
    private boolean isDefaultImageSize = true;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imgPhoto = findViewById(R.id.imgPhoto);
        imgPhoto.setOnClickListener(this);
        findViewById(R.id.tvDelete).setOnClickListener(this);
        findViewById(R.id.tvClose).setOnClickListener(this);

        fileName = getIntent().getExtras().getString("fileName");
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        imgPhoto.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvDelete:
                if (new File(fileName).delete()) {
                    Toast.makeText(PhotoActivity.this, "Фото успешно удалено",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PhotoActivity.this,
                            ProfileActivity.class));
                } else {
                    Toast.makeText(PhotoActivity.this, "Фото не было удалено",
                            Toast.LENGTH_SHORT).show();
                }
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