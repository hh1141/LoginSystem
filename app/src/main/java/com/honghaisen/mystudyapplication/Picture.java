package com.honghaisen.mystudyapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class Picture extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        imageView = (ImageView) findViewById(R.id.pic);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // get the dimensions fo the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        //Get the dimensions of the bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        String path = (String)getIntent().getExtras().get("path");
        BitmapFactory.decodeFile(path, options);
        int photoW = options.outWidth;
        int photoH = options.outHeight;
        Log.d("width", String.valueOf(targetW));
        Log.d("height", String.valueOf(targetH));
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;

        Bitmap image = BitmapFactory.decodeFile(path, options);
        if(image != null) {
            Log.d("bitmap", image.toString());
            imageView.setImageBitmap(image);
        }
    }
}
