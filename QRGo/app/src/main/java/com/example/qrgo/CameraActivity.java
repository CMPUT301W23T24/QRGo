package com.example.qrgo;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for the camera functionality
 */
public class CameraActivity extends AppCompatActivity {

    private Button captureButton;
    private Button finishButton;
    private ImageView image;
    private ActivityResultLauncher<Uri> takePictureLauncher;
    Uri imageUri;
    private final static int WRITE_CODE = 100;
    OutputStream outputStream;
    private String hash;
    private String userId;
    private String imageName;
    private File directory;
    private File imagePath;

    private FirebaseFirestore db;
    private DocumentReference qrRef;
    private Bitmap bitmap;


    /**
     * Creates the activity for the camera
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main);
        captureButton = findViewById(R.id.take_pic_btn);
        finishButton = findViewById(R.id.finish_btn);

        hash = getIntent().getStringExtra("hash");
        userId = getIntent().getStringExtra("userId");
        db = FirebaseFirestore.getInstance();
        qrRef = db.collection("qr").document(hash);

        checkImageExist();

        image = findViewById(R.id.image);

        imageUri = createUri();
        registerPictureLauncher();

        captureButton.setOnClickListener(view -> {
            takePictureLauncher.launch(imageUri);
        });

        finishButton.setOnClickListener(view -> {
            saveToDb();
        });
    }


    /**
     * creates the Url file
     * @return
     */
    private Uri createUri() {
        File imageFile = new File(getApplicationContext().getFilesDir(), "camera_photo.jpg");
        return FileProvider.getUriForFile(
                getApplicationContext(),
                "com.example.camerapermission.fileProvider", imageFile);
    }

    /**
     * register the launcher to take a picture
     */
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

    /**
     * Saves the image to the database
     */
    public void saveToDb() {

        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        String base64String = convertBitmapToString(bitmap);


        Map<String, String> photoMap = new HashMap<>();
        photoMap.put(userId, base64String);
        Log.d("hash", hash);
        Log.d("base64string: ", photoMap.toString());

        qrRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        Log.d(TAG, "exists!");
                        qrRef.update("photos", FieldValue.arrayUnion(photoMap));
                    } else {
                        Log.d(TAG, "DNE");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

        finish();

    }

    /**
     * converts the bitmap image into a string
     * @param bitmap
     * @return bitmap as a string
     */
    public String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * converts a string image to a bitmap
     * @param base64String
     * @return bitmap
     */
    public Bitmap convertStringToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, new BitmapFactory.Options());
    }

    /**
     * verifies if an image already exists within the database
     */
    private void checkImageExist() {
        qrRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        ArrayList<HashMap<String, String>> photosList = (ArrayList<HashMap<String, String>>) doc.get("photos");
                        if (photosList != null) {
                            for (HashMap<String, String> photoMap : photosList) {
                                if (photoMap.containsKey(userId)) {
                                    // User has a photo with base64string
                                    String base64String = photoMap.get(userId);
                                    // Do something with the base64string here
                                    Log.d("retrieved string", base64String);
                                    bitmap = convertStringToBitmap(base64String);
                                    image.setImageBitmap(bitmap);
                                    break;
                                }
                            }
                        }
                    } else {
                        Log.d(TAG, "DNE");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }
}
