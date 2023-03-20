package com.example.championship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class AdapterPhotos extends BaseAdapter {

    private Context context;
    private File[] fileNames;

    public AdapterPhotos(Context context, File[] fileNames) {

        this.context = context;
        this.fileNames = fileNames;
    }

    @Override
    public int getCount() {
        return fileNames.length;
    }

    @Override
    public Object getItem(int position) {
        return fileNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;
        if (fileNames[position] != null) {
            v = View.inflate(context, R.layout.item_photos_img, null);

            ImageView imgPhoto = v.findViewById(R.id.imgPhoto);
            TextView tvTime = v.findViewById(R.id.tvTime);

            String fileName = fileNames[position].toString();
            Bitmap bitmap = BitmapFactory.decodeFile(fileName);
            imgPhoto.setImageBitmap(bitmap);

            int indexDot = fileName.lastIndexOf('-');
            String time = fileName.substring(indexDot - 5, indexDot);
            time = time.replace('-', ':');
            tvTime.setText(time);
        } else {
            v = View.inflate(context, R.layout.item_photos_btn, null);
        }

        return v;
    }
}