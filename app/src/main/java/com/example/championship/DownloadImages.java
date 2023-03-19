package com.example.championship;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;

@SuppressLint("StaticFieldLeak")
public class DownloadImages extends AsyncTask<String, Void, Bitmap> {

    private ImageView image;
    private ProgressBar pbWait;

    public DownloadImages(ImageView imageView, ProgressBar pbWait) {

        image = imageView;
        this.pbWait = pbWait;
    }

    public Bitmap doInBackground(String... urls) {

        String urlDisplay = urls[0];
        Bitmap bitmap = null;

        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Ошибка", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {

        pbWait.setVisibility(View.GONE);
        image.setImageBitmap(result);
    }
}