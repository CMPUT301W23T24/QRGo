package com.example.qrgo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class ShowPhotoActivity extends AppCompatActivity {
    private ImageView image;
    private String hash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_photo);
        image = findViewById(R.id.showImage);
        hash = getIntent().getStringExtra("hash");
        Log.d("Bitch", "fuckkkk1");

        showImage();

    }

    private void showImage() {

        String filename = Environment.getExternalStorageDirectory() +"/SaveImage/"+ hash + ".jpg";
        Log.d("Bitch", "fuckkkk");
        Bitmap bitmap = BitmapFactory.decodeFile(filename);
        image.setImageBitmap(bitmap);
    }
}
