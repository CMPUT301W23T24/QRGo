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

    private QRReader qrContent;
    private String hash;
    private Integer score;
    private String face;
    private String name;
    private String userId;
    private String comments;

    QR qr;
    private User user;

    private FusedLocationProviderClient fusedLocationClient;

    private final static int LOCATION_PERMISSION_CODE = 100;

    /**
     * @author Ayaan
     * creates the activity needed to display the QR face, photos, locations and etc.
     * @param savedInstanceState remembers the profile of the QR
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_details);

        String content = getIntent().getStringExtra("qrContent");
        userId = getIntent().getStringExtra("userId");


        QRFaceTV = (TextView) findViewById(R.id.TVQRFace);
        nameTV = (TextView) findViewById(R.id.TVName);
        scoreTV = (TextView) findViewById(R.id.TVScore);

        locationB = (Button) findViewById(R.id.LocationButton);
        photoB = (Button) findViewById(R.id.PhotoButton);
        scannersB = (Button) findViewById(R.id.ScannersButton);
        commentsB = (Button) findViewById(R.id.CommentsButton);
        deleteB = (Button) findViewById(R.id.DeleteButton);


        qrContent = new QRReader();


        if (content != null && userId != null) {
            user = new User(userId);

            hash = qrContent.createHash(content);
            score = qrContent.calcScore(hash);
            face = qrContent.createFace(hash);
            name = qrContent.createName(hash);

            // mock user for testing
            //comments = "mfin uuuuuuuuuuuhhm";

            qr = new QR(name, userId, score, face);

            QRFaceTV.setText(face);
            nameTV.setText(name);
            scoreTV.setText(score.toString());
            qrContent.addToDB(hash, qr);



            user.getValuesFromDb(userId, new User.OnUserLoadedListener() {
                @Override
                public void onUserLoaded(User user) {

                }
            });

            //user.addQR(userId, hash);
            user.addQR(userId, hash, score);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        locationB.setOnClickListener(new View.OnClickListener() {
            /**
             * @author Faiyad

             * Upon clicking a the button provide the location of where the user is
             * @param view
             */
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        //https://www.youtube.com/watch?v=9XSlbZN1yFg&ab_channel=CodingZest
        //https://www.topcoder.com/thrive/articles/save-data-using-file-in-android
        photoB.setOnClickListener(new View.OnClickListener() {
            /**
             * @author Faiyad
             * Upon clicking the button to provide the photo of the QR
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRDetails.this, CameraActivity.class);
                intent.putExtra("hash", hash);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        scannersB.setOnClickListener(new View.OnClickListener() {
            /**
             * @author Amaan
             * Upon clicking a the button provide the location of where the user is
             * @param view
             */
            @Override
            public void onClick(View view) {
                //Listview fragment probably
                Intent intent = new Intent(QRDetails.this, ScannedDoop.class);
                intent.putExtra("hash", hash);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        commentsB.setOnClickListener(new View.OnClickListener() {
            /**
             * @author Amaan
             * Upon clicking a the button provide the location of where the user is
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRDetails.this, MainDoop.class);
                intent.putExtra("hash", hash);
                intent.putExtra("userId", userId);
                intent.putExtra("username", user.getUserName());
                startActivity(intent);
            }
        });

        deleteB.setOnClickListener(new View.OnClickListener() {
            /**
             * @author Ayaan
             * Upon clicking a the button provide the location of where the user is
             * @param view
             */
            @Override
            public void onClick(View view) {
                //TODO remove the QR from DB
                qrContent.removeFromDB(hash, qr);
                user.deleteQR(userId, hash, score);
//                Intent intent = new Intent(QRDetails.this, MainActivity.class);
//
//                startActivity(intent);
//                finish(); // need to ask ayaan if this is needed?
                Toast.makeText(QRDetails.this, "Deleted this QR from user", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * @author Faiyad
     * gets the location of the user provided they offer it
     */
    //https://www.youtube.com/watch?v=XfCbE4CyGVQ&t=53s&ab_channel=CodingwithAiman
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
                                qrContent.addLocationToDB(hash, userId, latitude, longitude);

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
     * @author Faiyad
     * asks the location of the user
     */
    private void askPermission() {
        ActivityCompat.requestPermissions(QRDetails.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
    }

    /**
     * @author Faiyad
     *  if the user accepts their location being requested they will get their location provided
     * @param requestCode The request code passed in askPermission()(
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
