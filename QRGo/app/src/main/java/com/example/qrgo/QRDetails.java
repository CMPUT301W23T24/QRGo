package com.example.qrgo;

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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class QRDetails extends AppCompatActivity {
    private TextView QRFaceTV;
    private TextView nameTV;
    private TextView scoreTV;
    private Button locationB;
    private Button photoB;
    private Button scannersB;
    private Button commentsB;
    private Button deleteB;

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

//        TODO after we hash we should check if it actually exists in the db, if it does we get the info as is else create it

        score = qrContent.calcScore(hash);
        face = qrContent.createFace(hash);
        name = qrContent.createName(hash);
        ArrayList<String> users = new ArrayList<>();
        ArrayList<String> comments = new ArrayList<>();
        users.add("user1");
        comments.add("mfin uuuuuuuuuuuuuuuh");
        QR qr = new QR(name, users, comments, score, face);

        QRFaceTV.setText(face);
        nameTV.setText(name);
        scoreTV.setText(score.toString());



        locationB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Not sure if anyone else set up a location class so I left it out
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
}
