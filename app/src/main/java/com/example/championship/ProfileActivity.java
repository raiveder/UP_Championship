package com.example.championship;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeComponent();

        findViewById(R.id.imgTest).setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {

            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = null;
                    Uri selectedImage = imageReturnedIntent.getData();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        Toast.makeText(ProfileActivity.this, "Ошибка: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    ImageView imgTest = findViewById(R.id.imgTest);
                    imgTest.setImageBitmap(bitmap);

                    saveImageFile(bitmap, "testFile.jpg");
                }
                break;
        }
    }

    private void saveImageFile(Bitmap bitmap, String fileName) {

        File direct = new File(getFilesDir() + "/UserPhoto/");

        if (!direct.exists()) {
            File newDir = new File(getFilesDir() + "/UserPhoto/");
            newDir.mkdirs();
        }

        File file = new File(direct, fileName);

        try {
            OutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            Toast.makeText(ProfileActivity.this, "Фото успешно добавлено",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(ProfileActivity.this, "Ошибка: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeComponent() {

        new DownloadImages(findViewById(R.id.imgAvatar), findViewById(R.id.pbWait)).
                execute(MainActivity.user.getAvatar());

        TextView tvName = findViewById(R.id.tvName);
        tvName.setText(MainActivity.user.getNickName());

        findViewById(R.id.tvExit).setOnClickListener(this);
        findViewById(R.id.imgListen).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvExit:
                saveEmail();
                startActivity(new Intent(ProfileActivity.this,
                        LoginActivity.class));
                break;

            case R.id.imgListen:
                startActivity(new Intent(ProfileActivity.this,
                        ListenActivity.class));
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