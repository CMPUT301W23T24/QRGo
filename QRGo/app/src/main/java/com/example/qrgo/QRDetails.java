package com.example.qrgo;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
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


public class QRDetails extends AppCompatActivity {
    private TextView QRFaceTV;
    private TextView nameTV;
    private TextView scoreTV;
    private Button locationB;
    private Button photoB;
    private Button scannersB;
    private Button commentsB;
    private Button deleteB;


    private FusedLocationProviderClient fusedLocationClient;

    private final static int LOCATION_PERMISSION_CODE = 100;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_details);
        String content = getIntent().getStringExtra("qrContent");
        String hash;
        Integer score;
        String face;
        String name;
        QRFaceTV = (TextView) findViewById(R.id.TVQRFace);
        nameTV = (TextView) findViewById(R.id.TVName);
        scoreTV = (TextView) findViewById(R.id.TVScore);

        locationB = (Button) findViewById(R.id.LocationButton);
        photoB = (Button) findViewById(R.id.PhotoButton);
        scannersB = (Button) findViewById(R.id.ScannersButton);
        commentsB = (Button) findViewById(R.id.CommentsButton);
        deleteB = (Button) findViewById(R.id.DeleteButton);

        QRReader qrContent = new QRReader();
        hash = qrContent.createHash(content);
        score = qrContent.calcScore(hash);
        face = qrContent.createFace(hash);
        name = qrContent.createName(hash);

        QR qr = new QR(name, "user", score, face);

        QRFaceTV.setText(face);
        nameTV.setText(name);
        scoreTV.setText(score.toString());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationB.setOnClickListener(new View.OnClickListener() {
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

                                // DB Stuff

                                AlertDialog.Builder builder = new AlertDialog.Builder(QRDetails.this);
                                builder.setTitle("Result");
                                builder.setMessage("Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude());
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

    private void askPermission() {
        ActivityCompat.requestPermissions(QRDetails.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        Log.d("2.", "1");

    }

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
