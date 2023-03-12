package com.example.qrgo;


import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


/**
 * Creates the Details needed to bring the QR class into a more profile based scan
 */
public class QRDetails extends AppCompatActivity {
    private TextView QRFaceTV;
    private TextView nameTV;
    private TextView scoreTV;
    private Button locationB;
    private Button photoB;
    private Button scannersB;
    private Button commentsB;
    private Button deleteB;
    private Button showPhotoBtn;

    QRReader qrContent;
    String hash;
    Integer score;
    String face;
    String name;
    String users;
    String comments;

    private FusedLocationProviderClient fusedLocationClient;

    private final static int LOCATION_PERMISSION_CODE = 100;

    /**
     * creates the activity needed to display the QR face, photos, locations and etc.
     * @param savedInstanceState remembers the profil of the QR
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_details);

        String content = getIntent().getStringExtra("qrContent");


        QRFaceTV = (TextView) findViewById(R.id.TVQRFace);
        nameTV = (TextView) findViewById(R.id.TVName);
        scoreTV = (TextView) findViewById(R.id.TVScore);

        locationB = (Button) findViewById(R.id.LocationButton);
        photoB = (Button) findViewById(R.id.PhotoButton);
        scannersB = (Button) findViewById(R.id.ScannersButton);
        commentsB = (Button) findViewById(R.id.CommentsButton);
        deleteB = (Button) findViewById(R.id.DeleteButton);


        qrContent = new QRReader();
        hash = qrContent.createHash(content);


        score = qrContent.calcScore(hash);
        face = qrContent.createFace(hash);
        name = qrContent.createName(hash);

        // mock user for testing
        users = "user1";
        comments = "mfin uuuuuuuuuuuhhm";

        QR qr = new QR(name, users, score, face);

        QRFaceTV.setText(face);
        nameTV.setText(name);
        scoreTV.setText(score.toString());
        qrContent.addToDB(hash, qr);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);




        locationB.setOnClickListener(new View.OnClickListener() {
            /**
             * Upon clickiing a the button provide the location of where the user is
             * @param view
             */
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });


        photoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO As before a class might exist for this (maybe Faiyad)?
                //TODO Also might be easier to make a fragment over instead of creating a whole new activity
                Intent intent = new Intent(QRDetails.this, CameraActivity.class);
                intent.putExtra("hash", hash);
                startActivity(intent);
            }
        });




        scannersB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO activity? Fragment?
                //Listview fragment probably
            }
        });

        commentsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO activity? Fragment?
            }
        });

        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO remove the QR from DB
            }
        });

    }

    /**
     * gets the location of the user provided they offer it
     */
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Handle permission not granted
            askPermission();
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                // DB Stuff
                                qrContent.addLocationToDB(hash, users, latitude, longitude);

                                AlertDialog.Builder builder = new AlertDialog.Builder(QRDetails.this);
                                builder.setTitle("Result");
                                builder.setMessage("Latitude: " + latitude + "\nLongitude: " + longitude);
                                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.show();
                            }
                        }
                    });
        }


    }

    /**
     * asks the location of the user
     */
    private void askPermission() {
        ActivityCompat.requestPermissions(QRDetails.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        Log.d("2.", "1");

    }

    /**
     *  if the user accepts their location being requested they will get their location provided
     * @param requestCode The request code passed in {@link #askPermission()(
     * android.app.Activity, String[], int)}
     * @param permissions The requested permissions. Never null.
     * @param grantRequest The grant results for the corresponding permissions
     *     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     *
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantRequest) {
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantRequest.length > 0 && grantRequest[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(QRDetails.this, "Location permission required", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantRequest);
    }
}
