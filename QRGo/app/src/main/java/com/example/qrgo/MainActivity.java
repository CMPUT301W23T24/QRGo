package com.example.qrgo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;



import android.widget.TextView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Starting page of the App
 */
public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    Button scanQRButton;
    Button searchQR;
    Button viewProfile;
    Button findFriends;
    Button scannedCodes;
    Button scoreboards;
    Button viewMap;
    String mId;

    /**
     * Creates the display for the app
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        db= FirebaseFirestore.getInstance();
        CollectionReference collectionReference1= db.collection("user");
        CollectionReference collectionReference2= db.collection("qr");

        User user= new User(mId);
        user.getValuesFromDb(mId, new User.OnUserLoadedListener() {
            /**
             * gets the values from the DB
             * @param user gets the information of the user
             */
            @Override
            public void onUserLoaded(User user) {
                //
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toastyboy(user);
            }
        }, 1500);



        viewProfile = findViewById(R.id.viewProfile);
        findFriends = findViewById(R.id.findFriends);
        scannedCodes = findViewById(R.id.scannedCodes);
        viewMap = findViewById(R.id.viewMapButton);
        scannedCodes.setOnClickListener(new View.OnClickListener() {
            /**
             * open up the users scanned codes
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScannedCodesActivity.class);
                intent.putExtra("userId", mId);
                startActivity(intent);
            }
        });

        viewProfile.setOnClickListener(new View.OnClickListener() {
            /**
             * open up the profile of a user
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewProfile.class);
                startActivity(intent);
            }
        });

        findFriends.setOnClickListener(new View.OnClickListener() {
            /**
             * Find the friends of a user
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FindFriends.class);
                startActivity(intent);
            }
        });

        // set up scanQR button functionality
         scanQRButton = findViewById(R.id.scanQRButton);
         searchQR = findViewById(R.id.goToSearchB);
         scanQRButton.setOnClickListener(new View.OnClickListener() {
             /**
              * open up the camera and scan a qrCode
              * @param view
              */
             @Override
             public void onClick(View view) {
                 scanQRCode();
             }
         });

        searchQR.setOnClickListener(new View.OnClickListener() {
            /**
             * Search for qr codes
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchQR.class);
                startActivity(intent);
            }
        });

        scoreboards = findViewById(R.id.scoreboards_button);
        scoreboards.setOnClickListener(new View.OnClickListener() {
            /**
             * open up the leaderboards/scoreboards
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScoreBoardDoop.class);
                intent.putExtra("userId", mId);
                startActivity(intent);
            }});
        
        
        
            viewMap.setOnClickListener(new View.OnClickListener() {
                /**
                 * view the map activity
                 * @param view
                 */
                @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public void toastyboy(User user){
        if (user.getUserName().equals("username")){
            Toast.makeText(getApplicationContext(), "Please edit your profile to set a unique username", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * sets up the options and launches the cameraActivity
     */
    private void scanQRCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a QR Code");
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureActivity.class);
        barLauncher.launch(options);
    }

    /**
     * If we find something we add the content into the QR content
     */
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        // scan the code and pass its result to the qr details activity
       if (result.getContents() != null) {
           String qrContent = result.getContents();
           Intent intent = new Intent(this, QRDetails.class);
           intent.putExtra("qrContent", qrContent);
           intent.putExtra("userId", mId);
           startActivity(intent);
       }
    });
}