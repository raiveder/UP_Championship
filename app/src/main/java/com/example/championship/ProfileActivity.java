package com.example.championship;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
public class ProfileActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener {

    private GridView gvImages;
    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeComponent();
    }

    private void initializeComponent() {

        new DownloadImages(findViewById(R.id.imgAvatar), findViewById(R.id.pbWait)).
                execute(MainActivity.user.getAvatar());

        TextView tvName = findViewById(R.id.tvName);
        tvName.setText(MainActivity.user.getNickName());

        gvImages = findViewById(R.id.gvImages);
        gvImages.setOnItemClickListener(this);
        findViewById(R.id.tvExit).setOnClickListener(this);
        findViewById(R.id.imgListen).setOnClickListener(this);

        File direct = new File(getFilesDir() + "/UserPhoto");
        AdapterPhotos adapter = new AdapterPhotos(this, direct.listFiles());
        gvImages.setAdapter(adapter);
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
                        saveImageFile(bitmap);
                    } catch (IOException e) {
                        Toast.makeText(ProfileActivity.this, "Ошибка: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void saveImageFile(Bitmap bitmap) {

        String directoryString = getFilesDir() + "/UserPhoto/";

        File directoryFile = new File(directoryString);

        if (!directoryFile.exists()) {
            directoryFile.mkdirs();
        }

        Date currentDate = new Date();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        currentDate.setHours(currentDate.getHours() + 3);
        String time = timeFormat.format(currentDate);
        time = time.replace(':', '-');

        File file = new File(directoryString + System.currentTimeMillis() + time + ".jpg");

        try {
            OutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            AdapterPhotos adapter = new AdapterPhotos(this, directoryFile.listFiles());
            gvImages.setAdapter(adapter);

            Toast.makeText(ProfileActivity.this, "Фото успешно добавлено",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(ProfileActivity.this, "Ошибка: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }
}