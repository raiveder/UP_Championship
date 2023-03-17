package com.example.championship;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImages extends AsyncTask<String, Void, Bitmap> {

    private ImageView image;

    public DownloadImages(ImageView imageView) {

        image = imageView;
    }

    public Bitmap doInBackground(String... urls) {

        String urldisplay = urls[0];
        Bitmap bitmap = null;

        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Ошибка", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {

        image.setImageBitmap(result);
    }
}