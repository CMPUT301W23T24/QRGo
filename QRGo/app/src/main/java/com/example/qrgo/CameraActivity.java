package com.example.qrgo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.firebase.firestore.core.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CameraActivity extends AppCompatActivity {

    private Button captureButton;
    private Button finishButton;
    private ImageView image;
    private ActivityResultLauncher<Uri> takePictureLauncher;
    Uri imageUri;
    private final static int WRITE_CODE = 100;
    OutputStream outputStream;
    private String hash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main);
        captureButton = findViewById(R.id.take_pic_btn);
        finishButton = findViewById(R.id.finish_btn);

        hash = getIntent().getStringExtra("hash");

        image = findViewById(R.id.image);
        imageUri = createUri();
        registerPictureLauncher();
        captureButton.setOnClickListener(view -> {
            takePictureLauncher.launch(imageUri);
        });
        finishButton.setOnClickListener(view -> {

            /////
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                // Handle permission not granted
                askPermission();
            } else {
                saveFile();
            }

            finish();

        });
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_CODE);
        Log.d("2.", "1");
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantRequest) {
        if (requestCode == WRITE_CODE) {
            if (grantRequest.length > 0 && grantRequest[0] == PackageManager.PERMISSION_GRANTED) {
                saveFile();
            } else {
                Toast.makeText(CameraActivity.this, "Write permission required", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantRequest);
    }

    private void saveFile () {
        File dir = new File(Environment.getExternalStorageDirectory(), "SaveImage");
        if (!dir.exists()){
            dir.mkdir();
        }
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap =drawable.getBitmap();
        File file = new File(dir, hash+".jpg");
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        Toast.makeText(CameraActivity.this, "Successfully saved", Toast.LENGTH_SHORT).show();

        try {
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Uri createUri() {
        File imageFile = new File(getApplicationContext().getFilesDir(), "camera_photo.jpg");
        Log.d("hello", imageFile.toString());
        return FileProvider.getUriForFile(
                getApplicationContext(),
                "com.example.camerapermission.fileProvider", imageFile);

    }

    private void registerPictureLauncher() {
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        try {
                            if (result) {
                                image.setImageURI(imageUri);
                            }
                        } catch (Exception exception) {
                            exception.getStackTrace();
                        }
                    }
                }
        );
    }
}
